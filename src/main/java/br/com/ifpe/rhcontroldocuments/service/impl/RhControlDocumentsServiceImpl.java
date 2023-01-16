package br.com.ifpe.rhcontroldocuments.service.impl;


import br.com.ifpe.rhcontroldocuments.config.TopicosKafka;
import br.com.ifpe.rhcontroldocuments.model.DiaAbonado;
import br.com.ifpe.rhcontroldocuments.model.Documento;
import br.com.ifpe.rhcontroldocuments.model.Funcionario;
import br.com.ifpe.rhcontroldocuments.model.MessageKafka;
import br.com.ifpe.rhcontroldocuments.repository.DocumentoRepository;
import br.com.ifpe.rhcontroldocuments.repository.FuncionarioRepository;
import br.com.ifpe.rhcontroldocuments.service.RhControlDocumentsService;
import br.com.ifpe.rhcontroldocuments.utils.ConvertDiasAbonados;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class RhControlDocumentsServiceImpl implements RhControlDocumentsService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    ConvertDiasAbonados convertDiasAbonados;

    @Autowired
    private KafkaTemplate<String, Serializable> kafkaTemplate;


    public Funcionario putDocumentS3(Long codigoFuncionario, MultipartFile multipartFile, String diasAbonados) throws Exception {

        String documentKey = UUID.randomUUID().toString();

        Funcionario funcionario = new Funcionario();
        funcionario.setCodigoFuncionario(codigoFuncionario);
        List<Documento> documentos = new ArrayList<>();

        Funcionario func = funcionarioRepository.findById(codigoFuncionario).orElse(funcionario);

        if (Objects.nonNull(func.getDocumentos())) {
            documentos.addAll(func.getDocumentos());
        }

        File file = this.convertMultiPartToFile(multipartFile);
        amazonS3.putObject("rh-control-bucket", documentKey, file);

        List<DiaAbonado> convertedDiasAbonados = convertDiasAbonados.convert(diasAbonados);

        Documento documento = new Documento();
        documento.setCodigoDocumento(documentKey);
        documento.setUrlDocumento(getObjectURL(documentKey));
        documento.setCodFuncionario(codigoFuncionario);
        documento.setDataEnvio(LocalDate.now());
        documento.setDiasAbonados(convertedDiasAbonados);
        documento.setDocumentoAprovado(false);

        documentos.add(documento);
        funcionario.setDocumentos(documentos);

        documentoRepository.save(documento);
        funcionarioRepository.save(funcionario);

        MessageKafka messageKafka = new MessageKafka();
        messageKafka.setCodigoFuncionario(codigoFuncionario);
        messageKafka.setDiasAbonados(convertedDiasAbonados);
        messageKafka.setStatus("ATESTADO_EM_ANALISE");

        kafkaTemplate.send(TopicosKafka.ATESTADO_TOPICO, messageKafka);

        return funcionario;
    }

    public Funcionario approveDocument(String codigoDocumento, Long codigoFuncionario) throws Exception {

        Funcionario funcionario = funcionarioRepository.findById(codigoFuncionario).orElseThrow(() -> new Exception("Funcionário não existe"));
        MessageKafka messageKafka = new MessageKafka();

        funcionario.getDocumentos().forEach(documento -> {
            if (documento.getCodigoDocumento().equalsIgnoreCase(codigoDocumento)) {
                documento.setDocumentoAprovado(true);

                messageKafka.setCodigoFuncionario(codigoFuncionario);
                messageKafka.setDiasAbonados(documento.getDiasAbonados());
                messageKafka.setStatus("ATESTADO");
            }
        });

        funcionarioRepository.save(funcionario);

        kafkaTemplate.send(TopicosKafka.ATESTADO_TOPICO, messageKafka);

        return funcionario;
    }

    public Funcionario getFuncionarioById(Long codigoFuncionario) throws Exception {
        Funcionario funcionario = funcionarioRepository.findById(codigoFuncionario).orElseThrow(() -> new Exception("Funcionário não existe"));
        return funcionario;
    }

    public Documento getDocumentoById(String codigoDocumento) throws Exception {
        Documento documento = documentoRepository.findById(codigoDocumento).orElseThrow(() -> new Exception("Documento não existe"));
        return documento;
    }

    private String getObjectURL(String objectKey) {
        String objectUrl = new StringBuilder()
                .append("https://union-profile-images.s3.amazonaws.com/")
                .append(objectKey)
                .toString();

        return objectUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

}
