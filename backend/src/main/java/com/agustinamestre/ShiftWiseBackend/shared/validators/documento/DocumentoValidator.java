package com.agustinamestre.ShiftWiseBackend.shared.validators.documento;


import com.agustinamestre.ShiftWiseBackend.shared.validators.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class DocumentoValidator extends BaseValidator implements ConstraintValidator<Documento, String> {

    @Override
    public boolean isValid(String documento, ConstraintValidatorContext context) {
        if(isBlank(documento)){
            changeDefaultMessage(context, "El numero de documento es requerido.");
            return false;
        }

        if (!isNumeric(documento)) {
            changeDefaultMessage(context, "Numero de cocumento invalido.");
            return false;
        }

        if(documento.length() != 8){
            changeDefaultMessage(context, "El numero de documento debe tener 8 digitos.");
            return false;
        }

        return true;
    }
}
