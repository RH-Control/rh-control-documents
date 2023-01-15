package br.com.ifpe.rhcontroldocuments.utils;

import br.com.ifpe.rhcontroldocuments.model.DiaAbonado;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ConvertDiasAbonados {

    @Autowired
    private ObjectMapper mapper;
    public List<DiaAbonado> convert(String value) throws JsonProcessingException {

        List<DiaAbonado> convertedDiasAbonados = mapper.readValue(value, new TypeReference<List<DiaAbonado>>() {
        });
        return convertedDiasAbonados;
    }

}
