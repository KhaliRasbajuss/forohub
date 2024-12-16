CREATE TABLE topico(
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL, 
    mensaje  VARCHAR(500) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status ENUM("ABIERTO", "CERRADO", "DESTACADO") NOT NULL DEFAULT "ABIERTO",
    usuario_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_topico_usuario_id FOREIGN KEY(usuario_id) REFERENCES usuario(id),
    CONSTRAINT fk_topico_curso_id FOREIGN KEY(curso_id) REFERENCES curso(id)
);
