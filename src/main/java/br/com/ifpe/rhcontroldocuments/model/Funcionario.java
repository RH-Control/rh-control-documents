package br.com.ifpe.rhcontroldocuments.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Funcionario {
    @Id
    @Column(name = "codigo_funcionario")
    private Long codigoFuncionario;

    @ElementCollection
    private List<Documento> documentos;

    public Long getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(Long codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }
}
