package br.org.editora.model.service;

import br.org.editora.model.DAO.SystemSearchDAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Obra;

public class SystemSearchService {

    private SystemSearchDAO dao = new SystemSearchDAO();

    public Obra buscarObraPorId(int id){

        if(id <= 0){
            throw new IllegalArgumentException("ID inválido");
        }

        return dao.buscarObraPorId(id);
    }

    public void buscarObraPorTitulo(String titulo){

        if(titulo == null || titulo.isEmpty()){
            throw new IllegalArgumentException("Título inválido");
        }

        dao.buscarObraPorTitulo(titulo);
    }

    public void buscarObraPorAno(int ano){

        if(ano <= 0){
            throw new IllegalArgumentException("Ano inválido");
        }

        dao.buscarObraPorAno(ano);
    }

    public void buscarObraPorStatus(String status){

        if(status == null || status.isEmpty()){
            throw new IllegalArgumentException("Status inválido");
        }

        dao.buscarObraPorStatus(status);
    }

    public void buscarObrasPorAutor(String nome){

        if(nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome inválido");
        }

        dao.buscarObrasPorAutor(nome);
    }

    public Autor buscarAutorPorNome(String nome){

        if(nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome inválido");
        }

        return dao.buscarAutorPorNome(nome);
    }

    public Avaliador buscarAvaliadorPorNome(String nome){

        if(nome == null || nome.isEmpty()){
            throw new IllegalArgumentException("Nome inválido");
        }

        return dao.buscarAvaliadorPorNome(nome);
    }

    public void listarTodasObras(){
        dao.listarTodasObras();
    }

    public void listarAutores(){
        dao.listarAutores();
    }

    public void listarAvaliadores(){
        dao.listarAvaliadores();
    }
}