package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;

import static java.util.Objects.isNull;
import org.springframework.stereotype.Component;

@Component
public class ValidadorJornadaNormalStrategy implements ValidadorJornada{

    @Override
    public void validar(Integer horas) {
        if (isNull(horas)) {
            throw new BusinessException(ShiftWiseErrors.HS_TRABAJADAS_REQUERIDAS);
        }

        if (horas < 6 || horas > 8) {
            throw new BusinessException(ShiftWiseErrors.HORAS_NORMAL);
        }
    }
}
