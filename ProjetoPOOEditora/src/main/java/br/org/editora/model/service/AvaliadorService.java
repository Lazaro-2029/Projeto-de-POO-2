package br.org.editora.model.service;

import br.org.editora.model.DAO.AvaliadorDAO;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Usuario;

import java.util.List;

public class AvaliadorService {

    private AvaliadorDAO dao = new AvaliadorDAO();

    // Cadastrar avaliador
    public Avaliador cadastrarAvaliador(Usuario usuario) {

        Avaliador existente = dao.buscarPorCpf(usuario.getCpf());

        if (existente != null) {
            throw new RuntimeException(
                    "Já existe um avaliador cadastrado com esse CPF."
            );
        }

        Avaliador avaliador = new Avaliador(usuario);

        return dao.inserir(avaliador);
    }

    // Buscar avaliador por CPF
    public Avaliador buscarPorCpf(String cpf) {

        Avaliador avaliador = dao.buscarPorCpf(cpf);

        if (avaliador == null) {
            throw new RuntimeException(
                    "Avaliador não encontrado."
            );
        }

        return avaliador;
    }

    // Buscar avaliador por nome
    public Avaliador buscarPorNome(String nome) {

        Avaliador avaliador = dao.buscarPorNome(nome);

        if (avaliador == null) {
            throw new RuntimeException(
                    "Avaliador não encontrado."
            );
        }

        return avaliador;
    }

    // Listar todos os avaliadores
    public List<Avaliador> listarAvaliadores() {
        return dao.listarTodos();
    }

    // Remover avaliador
    public void removerAvaliador(String cpf) {

        Avaliador avaliador = dao.buscarPorCpf(cpf);

        if (avaliador == null) {
            throw new RuntimeException(
                    "Avaliador não encontrado."
            );
        }

        dao.excluir(cpf);
    }
}