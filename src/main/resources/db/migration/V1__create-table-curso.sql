-- Active: 1733252923567@@localhost@3306
CREATE TABLE curso(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    categoria ENUM("BASE DE DATOS", "BIG DATA", "BACKEND", "FRONTEND", "SIN CATEGORIA") NOT NULL DEFAULT "SIN CATEGORIA" ,
    PRIMARY KEY(id)
);
