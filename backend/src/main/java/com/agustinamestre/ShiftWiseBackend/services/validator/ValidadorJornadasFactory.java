package com.agustinamestre.ShiftWiseBackend.services.validator;

import com.agustinamestre.ShiftWiseBackend.domain.ConceptoType;
import org.springframework.stereotype.Component;

@Component
public class ValidadorJornadasFactory {

    final ValidadorJornadaLibreStrategy validadorJornadaLibreStrategy;
    final ValidadorJornadaNormalStrategy validadorJornadaNormalStrategy;
    final ValidadorJornadaExtraStrategy validadorJornadaExtraStrategy;

    public ValidadorJornadasFactory(ValidadorJornadaLibreStrategy validadorJornadaLibreStrategy,
                                    ValidadorJornadaNormalStrategy validadorJornadaNormalStrategy,
                                    ValidadorJornadaExtraStrategy validadorJornadaExtraStrategy)
    {
        this.validadorJornadaLibreStrategy = validadorJornadaLibreStrategy;
        this.validadorJornadaNormalStrategy = validadorJornadaNormalStrategy;
        this.validadorJornadaExtraStrategy = validadorJornadaExtraStrategy;
    }

    public ValidadorJornada obtenerValidador(ConceptoType conceptoType) {
        switch (conceptoType) {
            case LIBRE -> {
                return validadorJornadaLibreStrategy;
            }
            case NORMAL -> {
                return validadorJornadaNormalStrategy;
            }
            case EXTRA -> {
                return validadorJornadaExtraStrategy;
            }
            default -> throw new IllegalArgumentException("");
        }
    }
}
