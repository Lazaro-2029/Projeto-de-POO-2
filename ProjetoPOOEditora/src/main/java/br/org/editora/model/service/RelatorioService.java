package br.org.editora.model.service;

import br.org.editora.model.DAO.RelatorioDAO;

public class RelatorioService {

    private RelatorioDAO dao = new RelatorioDAO();

    // Total de obras
    public int totalObras() {
        return dao.totalObras();
    }

    // Total de autores
    public int totalAutores() {
        return dao.totalAutores();
    }

    // Total de avaliadores
    public int totalAvaliadores() {
        return dao.totalAvaliadores();
    }

    // Relatório de status das obras
    public void relatorioStatusObras() {

        if (dao.totalObras() == 0) {
            throw new RuntimeException(
                    "Não existem obras cadastradas."
            );
        }

        dao.relatorioStatusObras();
    }

    // Relatório de obras por autor
    public void relatorioObrasPorAutor() {

        if (dao.totalAutores() == 0) {
            throw new RuntimeException(
                    "Não existem autores cadastrados."
            );
        }

        dao.relatorioObrasPorAutor();
    }

    // Relatório de obras por avaliador
    public void relatorioObrasPorAvaliador() {

        if (dao.totalAvaliadores() == 0) {
            throw new RuntimeException(
                    "Não existem avaliadores cadastrados."
            );
        }

        dao.relatorioObrasPorAvaliador();
    }

    // Relatório geral
    public void gerarRelatorioGeral() {

        System.out.println("===== RELATÓRIO GERAL DO SISTEMA =====");

        System.out.println("Total de obras: "
                + dao.totalObras());

        System.out.println("Total de autores: "
                + dao.totalAutores());

        System.out.println("Total de avaliadores : "
                + dao.totalAvaliadores());

        System.out.println("--------------------------------------");

        dao.relatorioStatusObras();

        System.out.println("--------------------------------------");

        dao.relatorioObrasPorAutor();

        System.out.println("--------------------------------------");

        dao.relatorioObrasPorAvaliador();

        System.out.println("======================================");
    }
}