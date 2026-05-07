package com.loveu.loveu.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mensaje {
    // Mensaje pertenece a un match y tiene un emisor y un receptor, ambos perfiles. El contenido del mensaje se guarda junto con la fecha de envio y el estado de lectura.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="match_id", referencedColumnName="id", nullable=false)
    private Match match;

    @ManyToOne
    @JoinColumn(name="perfil_emisor_id", referencedColumnName="id", nullable=false)
    private Perfil perfilEmisor;

    @ManyToOne
    @JoinColumn(name="perfil_receptor_id", referencedColumnName="id", nullable=false)
    private Perfil perfilReceptor;

    @Column(nullable = false, length = 1000)
    private String contenido;

    @Column(name = "sent_at", nullable = false) // SentAt a que hora y día fue enviado el mensaje
    private LocalDateTime sentAt;

    @Column(name = "is_read", nullable = false)
    private boolean read = false;
    // Ante s deguardar el mensaje, se asigna automaticamente la fecha de envio si viene vacia.
    @PrePersist
    public void prePersist(){
        this.sentAt = LocalDateTime.now(); }
}