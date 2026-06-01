package br.org.editora.model.service;

import br.org.editora.model.DAO.AutorDAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Usuario;

import java.util.List;

public class AutorService {

    private AutorDAO dao = new AutorDAO();

    // Cadastrar autor
    public Autor cadastrarAutor(Usuario usuario) {

        Autor existente = dao.buscarPorCpf(usuario.getCpf());

        if (existente != null) {
            throw new RuntimeException("Já existe um  autor cadastrado com esse CPF.");
        }

        Autor autor = new Autor(usuario);

        return dao.inserir(autor);
    }

    // Buscar autor por CPF
    public Autor buscarPorCpf(String cpf) {

        Autor autor = dao.buscarPorCpf(cpf);

        if (autor == null) {
            throw new RuntimeException("Autor não encontrado.");
        }

        return autor;
    }

    // Buscar autor por nome
    public Autor buscarPorNome(String nome) {

        Autor autor = dao.buscarPorNome(nome);

        if (autor == null) {
            throw new RuntimeException("Autor não encontrado.");
        }

        return autor;
    }

    // Listar todos os autores
    public List<Autor> listarAutores() {
        return dao.listarTodos();
    }

    // Remover autor
    public void removerAutor(String cpf) {

        Autor autor = dao.buscarPorCpf(cpf);

        if (autor == null) {
            throw new RuntimeException("Autor não encontrado.");
        }

        dao.excluir(cpf);
    }
}