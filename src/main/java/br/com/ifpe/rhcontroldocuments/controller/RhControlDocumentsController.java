package br.com.ifpe.rhcontroldocuments.controller;

import br.com.ifpe.rhcontroldocuments.model.Documento;
import br.com.ifpe.rhcontroldocuments.model.Funcionario;
import br.com.ifpe.rhcontroldocuments.service.RhControlDocumentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/documentos")
public class RhControlDocumentsController {

    @Autowired
    RhControlDocumentsService service;

    @PostMapping
    public ResponseEntity<Funcionario> uploadObjectS3(@RequestParam("file") MultipartFile file,
                                                      @RequestParam("codigoFuncionario") Long codigoFuncionario,
                                                      @RequestParam("diasAbonados") String diasAbonados
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.putDocumentS3(codigoFuncionario, file, diasAbonados));
    }

    @GetMapping("/approve")
    public ResponseEntity<Funcionario> approveDocument(@RequestParam("codigoDocumento") String codigoDocumento,
                                                       @RequestParam("codigoFuncionario") Long codigoFuncionario) throws Exception {
        return ResponseEntity.ok().body(service.approveDocument(codigoDocumento, codigoFuncionario));
    }

    @GetMapping("funcionario/{codigoFuncionario}")
    public ResponseEntity<Funcionario> getFuncionario(@PathVariable("codigoFuncionario") Long codigoFuncionario) throws Exception {
        return ResponseEntity.ok().body(service.getFuncionarioById(codigoFuncionario));
    }

    @GetMapping("funcionario/{codigoDocumento}")
    public ResponseEntity<Documento> getDocumento(@PathVariable("codigoFuncionario") String codigoDocumento) throws Exception {
        return ResponseEntity.ok().body(service.getDocumentoById(codigoDocumento));
    }
}
