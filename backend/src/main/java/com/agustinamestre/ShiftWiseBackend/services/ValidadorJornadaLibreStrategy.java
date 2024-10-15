package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;

import static java.util.Objects.isNull;
import org.springframework.stereotype.Component;

@Component
public class ValidadorJornadaLibreStrategy implements ValidadorJornada {

    @Override
    public void validar(Integer horas) {
        if (!isNull(horas)) {
            throw new BusinessException(ShiftWiseErrors.HS_TRABAJADAS_NO_REQUERIDAS);
        }
    }
}
