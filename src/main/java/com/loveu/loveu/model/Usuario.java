package com.loveu.loveu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    
    private Integer id;

}
