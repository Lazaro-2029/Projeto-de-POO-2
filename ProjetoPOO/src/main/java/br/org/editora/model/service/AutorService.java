package br.org.editora.model.service;

import br.org.editora.model.DAO.AutorDAO;
import br.org.editora.model.DAO.UsuarioDAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Usuario;
import java.util.List;

public class AutorService {
    private final AutorDAO autorDAO = new AutorDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Autor cadastrar(String cpf, String nome, String endereco) {
        if (cpf == null || cpf.isBlank())  throw new RuntimeException("CPF inválido.");
        if (nome == null || nome.isBlank()) throw new RuntimeException("Nome inválido.");
        if (endereco == null || endereco.isBlank()) throw new RuntimeException("Endereço inválido.");
        if (autorDAO.buscarPorCpf(cpf) != null) throw new RuntimeException("Autor já cadastrado com esse CPF.");

        Usuario u = usuarioDAO.buscarPorCpf(cpf);
        if (u == null) {
            u = new Usuario(cpf, nome, endereco);
            usuarioDAO.inserir(u);
        }
        Autor autor = new Autor(u);
        autorDAO.inserir(autor);
        return autor;
    }

    public Autor buscarPorCpf(String cpf) {
        return autorDAO.buscarPorCpf(cpf);
    }

    public Autor buscarPorNome(String nome) {
        return autorDAO.buscarPorNome(nome);
    }

    public List<Autor> listarTodos() {
        return autorDAO.listarTodos();
    }

    public void atualizar(String cpf, String novoNome, String novoEndereco) {
        if (autorDAO.buscarPorCpf(cpf) == null) throw new RuntimeException("Autor não encontrado.");
        usuarioDAO.atualizar(cpf, novoNome, novoEndereco);
    }

    public void excluir(String cpf) {
        if (autorDAO.buscarPorCpf(cpf) == null) throw new RuntimeException("Autor não encontrado.");
        autorDAO.excluir(cpf);
        if (usuarioDAO.detectarTipo(cpf) == null) usuarioDAO.excluir(cpf);
    }
}
