package com.agustinamestre.ShiftWiseBackend.models.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShiftWiseErrors {
    public static final String CODE_ERROR_TECNICO = "ErrorTecnico";
    public static final String CAMPO_CON_VALOR_INVALIDO = "Error en el campo {0}: {1}";
    public static final String CAMPO_REQUERIDO = "El campo {0} es requerido y no fue informado";
    public static final ApiError DOCUMENTO_EXISTENTE = new ApiError("DocumentoExistente","Ya existe un usuario con el documento ingresado.");
    public static final ApiError EMAIL_EXISTENTE = new ApiError("EmailExistente","Ya existe un usuario con el email ingresado.");
    public static final ApiError USER_NOT_FOUND = new ApiError("UsuarioNoEncontrado","No se encontró el usuario indicado.");
    public static final ApiError CONCEPTO_NOT_FOUND = new ApiError("ConceptoNoEncontrado","No se encontró el concepto indicado.");
    public static final ApiError PERFIL_NOT_FOUND = new ApiError("PerfilNoEncontrado","Perfil no encontrado");

    public static final ApiError HS_TRABAJADAS_REQUERIDAS = new ApiError("HorasTrabajadasObligatorias","Las horas trabajadas son obligatorias para el concepto ingresado.");
    public static final ApiError HS_TRABAJADAS_NO_REQUERIDAS = new ApiError("HorasTrabajadasNoRequeridas","El concepto ingresado no requiere el ingreso de horas trabajadas.");
    public static final ApiError HORAS_NORMAL = new ApiError("HorasConceptoNormal", "El rango de horas que se puede cargar para este concepto es de 6 - 8.");
    public static final ApiError HORAS_EXTRA = new ApiError("HorasConceptoExtra","El rango de horas que se puede cargar para este concepto es de 2 - 6.");

    public static final ApiError NO_DIA_LIBRE_EN_DIA_NORMAL = new ApiError("ErrorDiaLibre","El usuario no puede cargar Dia Libre si cargo un turno previamente para la fecha ingresada.");
    public static final ApiError JORNADA_CONCEPTO_REPETIDO = new ApiError("JornadaConceptoRepetido","El usuario ya tiene registrado una jornada con este concepto en la fecha ingresada.");
    public static final ApiError JORNADA_LIBRE = new ApiError("JornadaLibre","El usuario ingresado cuenta con un día libre en esa fecha");
    public static final ApiError NO_MAS_DE_12_HORAS_MISMO_DIA = new ApiError("HorasExcedidas","El usuario no puede cargar más de 12 horas trabajadas en un día.");
    public static final ApiError NO_MAS_DE_48_HORAS_POR_SEMANA = new ApiError("HorasExcedidas","El usuario ingresado supera las 48 horas semanales.");
    public static final ApiError NO_MAS_DE_3_TURNOS_EXTRA_POR_SEMANA = new ApiError("TurnosExtraExcedidos","El usuario ingresado ya cuenta con 3 turnos extra esta semana.");
    public static final ApiError NO_MAS_DE_5_TURNOS_NORMALES_POR_SEMANA = new ApiError("TurnosNormalExcedidos","El usuario ingresado ya cuenta con 5 turnos normales esta semana.");
    public static final ApiError NO_MAS_DE_2_TURNOS_LIBRE_POR_SEMANA = new ApiError("TurnosLibreExcedidos", "El usuario no cuenta con más días libres esta semana.");


}
