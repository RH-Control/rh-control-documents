package br.com.ifpe.rhcontroldocuments.model;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

public class MessageKafka implements Serializable {

    private static final long serialVersionUID = 2547660198120125434L;

    private Long codigoFuncionario;
    private List<DiaAbonado> diasAbonados;
    private String status;

    public Long getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(Long codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public List<DiaAbonado> getDiasAbonados() {
        return diasAbonados;
    }

    public void setDiasAbonados(List<DiaAbonado> diasAbonados) {
        this.diasAbonados = diasAbonados;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
