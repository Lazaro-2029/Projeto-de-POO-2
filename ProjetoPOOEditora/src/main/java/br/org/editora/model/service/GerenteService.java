package br.org.editora.model.service;

import br.org.editora.model.DAO.GerenteDAO;
import br.org.editora.model.entities.Gerente;
import br.org.editora.model.entities.Usuario;

import java.util.List;

public class GerenteService {

    private GerenteDAO dao = new GerenteDAO();

    // Cadastrar gerente
    public Gerente cadastrarGerente(Usuario usuario) {

        Gerente existente = dao.buscarPorCpf(usuario.getCpf());

        if (existente != null) {
            throw new RuntimeException(
                    "Já existe um gerente cadastrado com esse CPF."
            );
        }

        Gerente gerente = new Gerente(usuario);

        return dao.inserir(gerente);
    }

    // Buscar gerente por CPF
    public Gerente buscarPorCpf(String cpf) {

        Gerente gerente = dao.buscarPorCpf(cpf);

        if (gerente == null) {
            throw new RuntimeException(
                    "Gerente não encontrado."
            );
        }

        return gerente;
    }

    // Buscar gerente por nome
    public Gerente buscarPorNome(String nome) {

        Gerente gerente = dao.buscarPorNome(nome);

        if (gerente == null) {
            throw new RuntimeException(
                    "Gerente não encontrado."
            );
        }

        return gerente;
    }

    // Listar todos os gerentes
    public List<Gerente> listarGerentes() {
        return dao.listarTodos();
    }

    // Remover gerente
    public void removerGerente(String cpf) {

        Gerente gerente = dao.buscarPorCpf(cpf);

        if (gerente == null) {
            throw new RuntimeException(
                    "Gerente não encontrado."
            );
        }

        dao.excluir(cpf);
    }
}