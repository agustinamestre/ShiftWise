package com.agustinamestre.ShiftWiseBackend.domain;

import lombok.*;
import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter @Setter
@Entity(name = "conceptos")
public class Concepto {
    @Id
    private Integer id;

    private String nombre;

    private Boolean laborable;

    private Integer hsMinimo;

    private Integer hsMaximo;

    public ConceptoType obtenerConceptoType() {
        return ConceptoType.obtenerConceptoId(id);
    }
}
