-- Active: 1733252923567@@localhost@3306@db_forohub
CREATE TABLE perfil_usuario (
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_perfil_usuario_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario (id),
    CONSTRAINT fk_perfil_usuario_perfil_id FOREIGN KEY (perfil_id) REFERENCES perfil (id)
);