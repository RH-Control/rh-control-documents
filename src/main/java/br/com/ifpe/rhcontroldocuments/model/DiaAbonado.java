package br.com.ifpe.rhcontroldocuments.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Embeddable;
import java.time.LocalDate;
@Embeddable
public class DiaAbonado {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate diaAbonado;

    public LocalDate getDiaAbonado() {
        return diaAbonado;
    }

    public void setDiaAbonado(LocalDate diaAbonado) {
        this.diaAbonado = diaAbonado;
    }
}
