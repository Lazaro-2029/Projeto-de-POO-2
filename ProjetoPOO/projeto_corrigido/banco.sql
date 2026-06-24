DROP DATABASE IF EXISTS projetopoo;

CREATE DATABASE projetopoo
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE projetopoo;

CREATE TABLE usuario (
    cpf         VARCHAR(14)  NOT NULL PRIMARY KEY,
    nome        VARCHAR(120) NOT NULL,
    endereco    VARCHAR(255) NOT NULL,
    senha_hash  VARCHAR(64)  NOT NULL
);

CREATE TABLE gerente (
    cpf_usuario VARCHAR(14) NOT NULL PRIMARY KEY,
    CONSTRAINT fk_ger_usuario FOREIGN KEY (cpf_usuario) REFERENCES usuario(cpf) ON DELETE CASCADE
);

CREATE TABLE autor (
    cpf_usuario VARCHAR(14) NOT NULL PRIMARY KEY,
    CONSTRAINT fk_aut_usuario FOREIGN KEY (cpf_usuario) REFERENCES usuario(cpf) ON DELETE CASCADE
);

CREATE TABLE avaliador (
    cpf_usuario VARCHAR(14) NOT NULL PRIMARY KEY,
    CONSTRAINT fk_ava_usuario FOREIGN KEY (cpf_usuario) REFERENCES usuario(cpf) ON DELETE CASCADE
);

CREATE TABLE obra (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo          VARCHAR(200) NOT NULL,
    genero          VARCHAR(80)  NOT NULL,
    ano             INT          NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
    data_avaliacao  DATE         NULL,
    cpf_autor       VARCHAR(14)  NOT NULL,
    cpf_avaliador   VARCHAR(14)  NULL,
    CONSTRAINT fk_obra_autor     FOREIGN KEY (cpf_autor)     REFERENCES usuario(cpf),
    CONSTRAINT fk_obra_avaliador FOREIGN KEY (cpf_avaliador) REFERENCES avaliador(cpf_usuario)
);

INSERT INTO usuario (cpf, nome, endereco, senha_hash) VALUES
    ('000.000.000-00', 'Sr. Paulao',     'Rua da Editora, 100',    '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'),
    ('111.111.111-11', 'Joao Avaliador', 'Rua dos Avaliadores, 5', '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'),
    ('222.222.222-22', 'Maria Autora',   'Av. das Letras, 42',     '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251');

INSERT INTO gerente  (cpf_usuario) VALUES ('000.000.000-00');
INSERT INTO avaliador(cpf_usuario) VALUES ('111.111.111-11');
INSERT INTO autor    (cpf_usuario) VALUES ('222.222.222-22');

INSERT INTO obra (titulo, genero, ano, status, cpf_autor)
VALUES ('A Grande Aventura', 'Ficcao', 2024, 'PENDENTE', '222.222.222-22');

SELECT 'Banco criado! Login: 000.000.000-00  Senha: senha123' AS resultado;
