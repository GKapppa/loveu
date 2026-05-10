package com.loveu.loveu.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {

    private String primerNombre;
    private String primerApellido;
    private LocalDate fechaNacimiento;
    private String telefono;

}
