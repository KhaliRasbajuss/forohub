CREATE TABLE respuesta(
    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(500) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    topico_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_respuesta_topico_id FOREIGN KEY(topico_id) REFERENCES topico(id),
    CONSTRAINT fk_respuesta_usuario_id FOREIGN KEY(usuario_id) REFERENCES usuario(id)
);