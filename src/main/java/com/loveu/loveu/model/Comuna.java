package com.loveu.loveu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comunas")
public class Comuna {

    // Comuna pertenece a una Region y permtite ubicar de manera geografica un perfil.
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message="El nombre de la comuna no puede estar vacio!")
    @Column(name="nombre_comuna", length=50, nullable=false)
    @Size(max=50, message="El nombre de la comuna no puede pasar los 50 caracteres!")
    private String nombreComuna;

    @ManyToOne
    @JoinColumn(name="region_id", referencedColumnName="id", nullable=false) //Hacemos relacion Many to one ya que esta clase guarda el ID de Region 
    private Region region;
}
