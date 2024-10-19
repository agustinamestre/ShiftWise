package com.agustinamestre.ShiftWiseBackend.domain;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.JornadaRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
@Entity(name = "jornadas")
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idJornada;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser", referencedColumnName = "id")
    User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "jornada_concepto",
            joinColumns = @JoinColumn(name = "id_jornada"),
            inverseJoinColumns = @JoinColumn(name = "id_concepto")
    )
    private List<Concepto> conceptos = new ArrayList<>();

    @Column(nullable = false)
    LocalDate fecha;

    Integer horasTrabajadas;

    public void agregarConcepto(Concepto concepto){
        conceptos.add(concepto);
    }

    public static Jornada mapFromJornadaRequest(JornadaRequest request) {
        return Jornada.builder()
                .fecha(request.getFecha())
                .horasTrabajadas(request.getHorasTrabajadas())
                .conceptos(new ArrayList<>())
                .build();
    }
}
