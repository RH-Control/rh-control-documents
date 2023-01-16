package br.com.ifpe.rhcontroldocuments.service;

import br.com.ifpe.rhcontroldocuments.model.Documento;
import br.com.ifpe.rhcontroldocuments.model.Funcionario;
import org.springframework.web.multipart.MultipartFile;

public interface RhControlDocumentsService {
    public Funcionario putDocumentS3(Long codigoFuncionario, MultipartFile multipartFile, String diasAbonados) throws Exception;

    public Funcionario approveDocument(String codigoDocumento, Long codigoFuncionario) throws Exception;

    public Funcionario getFuncionarioById(Long codigoFuncionario) throws Exception;

    public Documento getDocumentoById(String codigoDocumento) throws Exception;
}
