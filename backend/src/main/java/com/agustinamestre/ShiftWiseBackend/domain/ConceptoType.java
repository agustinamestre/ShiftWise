package com.agustinamestre.ShiftWiseBackend.domain;


import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

@Getter
public enum ConceptoType {

    NORMAL(1), EXTRA(2), LIBRE(3);

    final Integer conceptoId;

    ConceptoType(Integer conceptoId) {
        this.conceptoId = conceptoId;
    }

    private static final Map<Integer, ConceptoType> CONCEPTO_TYPE_MAP = Stream.of(ConceptoType.values())
            .collect(toMap(ConceptoType::getConceptoId, conceptoType -> conceptoType));

    public static ConceptoType obtenerConceptoId(Integer conceptId) {
        return Optional.ofNullable(CONCEPTO_TYPE_MAP.get(conceptId))
                .orElseThrow(() -> new IllegalArgumentException("Concepto no válido: " + conceptId));
    }
}
