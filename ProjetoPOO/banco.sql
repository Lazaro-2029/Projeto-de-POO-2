-- ============================================================
-- Script de criação do banco de dados - Editora do Sr. Paulão
-- Execute este script antes de rodar o sistema.
-- ============================================================

CREATE DATABASE IF NOT EXISTS projetopoo
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE projetopoo;

-- Tabela base: todos os usuários do sistema
CREATE TABLE IF NOT EXISTS usuario (
    cpf       VARCHAR(14)  NOT NULL PRIMARY KEY,
    nome      VARCHAR(120) NOT NULL,
    endereco  VARCHAR(255) NOT NULL
);

-- Perfil: Gerente (Sr. Paulão)
CREATE TABLE IF NOT EXISTS gerente (
    cpf_usuario VARCHAR(14) NOT NULL PRIMARY KEY,
    CONSTRAINT fk_ger_usuario FOREIGN KEY (cpf_usuario) REFERENCES usuario(cpf) ON DELETE CASCADE
);

-- Perfil: Autor
CREATE TABLE IF NOT EXISTS autor (
    cpf_usuario VARCHAR(14) NOT NULL PRIMARY KEY,
    CONSTRAINT fk_aut_usuario FOREIGN KEY (cpf_usuario) REFERENCES usuario(cpf) ON DELETE CASCADE
);

-- Perfil: Avaliador
CREATE TABLE IF NOT EXISTS avaliador (
    cpf_usuario VARCHAR(14) NOT NULL PRIMARY KEY,
    CONSTRAINT fk_ava_usuario FOREIGN KEY (cpf_usuario) REFERENCES usuario(cpf) ON DELETE CASCADE
);

-- Obras literárias
CREATE TABLE IF NOT EXISTS obra (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    titulo          VARCHAR(200) NOT NULL,
    genero          VARCHAR(80)  NOT NULL,
    ano             INT          NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'PENDENTE',
    data_avaliacao  DATE         NULL,
    cpf_autor       VARCHAR(14)  NOT NULL,
    cpf_avaliador   VARCHAR(14)  NULL,
    CONSTRAINT fk_obra_autor    FOREIGN KEY (cpf_autor)     REFERENCES usuario(cpf),
    CONSTRAINT fk_obra_avaliador FOREIGN KEY (cpf_avaliador) REFERENCES avaliador(cpf_usuario)
);

-- ============================================================
-- Dados iniciais
-- ============================================================

-- Gerente: Sr. Paulão  (CPF de login: 000.000.000-00)
INSERT IGNORE INTO usuario  VALUES ('000.000.000-00', 'Sr. Paulão',       'Rua da Editora, 100');
INSERT IGNORE INTO gerente  VALUES ('000.000.000-00');

-- Avaliador de demonstração  (CPF de login: 111.111.111-11)
INSERT IGNORE INTO usuario   VALUES ('111.111.111-11', 'João Avaliador',   'Rua dos Avaliadores, 5');
INSERT IGNORE INTO avaliador VALUES ('111.111.111-11');

-- Autor de demonstração  (CPF de login: 222.222.222-22)
INSERT IGNORE INTO usuario VALUES ('222.222.222-22', 'Maria Autora',      'Av. das Letras, 42');
INSERT IGNORE INTO autor   VALUES ('222.222.222-22');

-- Obra de demonstração
INSERT IGNORE INTO obra (titulo, genero, ano, status, cpf_autor)
VALUES ('A Grande Aventura', 'Ficção', 2024, 'PENDENTE', '222.222.222-22');

SELECT 'Banco de dados criado com sucesso!' AS mensagem;
