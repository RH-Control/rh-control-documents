package br.com.ifpe.rhcontroldocuments.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Documento {
    @Id
    private String codigoDocumento;
    private String urlDocumento;
    private Long codFuncionario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataEnvio;
    @ElementCollection
    private List<DiaAbonado> diasAbonados;
    private Boolean documentoAprovado;


    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getUrlDocumento() {
        return urlDocumento;
    }

    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    public LocalDate getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public List<DiaAbonado> getDiasAbonados() {
        return diasAbonados;
    }

    public void setDiasAbonados(List<DiaAbonado> diasAbonados) {
        this.diasAbonados = diasAbonados;
    }

    public Boolean getDocumentoAprovado() {
        return documentoAprovado;
    }

    public void setDocumentoAprovado(Boolean documentoAprovado) {
        this.documentoAprovado = documentoAprovado;
    }

    public Long getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(Long codFuncionario) {
        this.codFuncionario = codFuncionario;
    }
}
