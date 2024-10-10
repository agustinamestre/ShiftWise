package com.agustinamestre.ShiftWiseBackend.controllers.responses;

import com.agustinamestre.ShiftWiseBackend.domain.Concepto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Getter @Builder
public class ConceptoDTO implements Serializable{
     Integer id;
     String nombre;
     Boolean laborable;
     Integer hsMinimo;
     Integer hsMaximo;

     public static ConceptoDTO mapFromConcepto(Concepto concepto){
          return ConceptoDTO.builder()
                  .id(concepto.getId())
                  .laborable(concepto.getLaborable())
                  .nombre(concepto.getNombre())
                  .hsMinimo(concepto.getHsMinimo())
                  .hsMaximo(concepto.getHsMaximo())
                  .build();
     }
}