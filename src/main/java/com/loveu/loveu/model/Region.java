package com.loveu.loveu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table(name="regiones")
@Entity
@Data
@Builder
public class Region {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre de la Region no puede estar vacio!")
    @Column(name="nombre_region", length=50, nullable=false)
    @Size(max= 50, message="El nombre de la region no puede contener más de 50 caracteres")
    private String nombreRegion;


    @NotBlank(message="La abreviacion de la region no puede estar vacio!")
    @Column(name="abreviacion", length=4, nullable=false)
    @Size(max=4, message="La abreviacion de la region no puede ser más de 4")
    private String abreviatura;
    
}
