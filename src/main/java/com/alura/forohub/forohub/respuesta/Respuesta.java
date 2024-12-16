package com.alura.forohub.forohub.respuesta;

import java.time.LocalDateTime;

import com.alura.forohub.forohub.topico.Topico;
import com.alura.forohub.forohub.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "respuesta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;


    @ManyToOne
    @JoinColumn(name = "topico_id", nullable = false)
    private Topico topico;
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    public Respuesta(DatosRegistrarRespuesta datos, Usuario usuario, Topico topico) {
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.topico = topico;
        this.usuario = usuario;
    }


}
