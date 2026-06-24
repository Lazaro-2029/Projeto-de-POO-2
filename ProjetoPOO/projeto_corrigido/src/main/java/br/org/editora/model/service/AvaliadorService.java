package br.org.editora.model.service;

import br.org.editora.model.DAO.AvaliadorDAO;
import br.org.editora.model.DAO.UsuarioDAO;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Usuario;
import br.org.editora.model.factory.UsuarioFactory;
import br.org.editora.model.util.SenhaUtil;
import java.util.List;

public class AvaliadorService {
    private final AvaliadorDAO avaliadorDAO = new AvaliadorDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Avaliador cadastrar(String cpf, String nome, String endereco) {
        return cadastrar(cpf, nome, endereco, "senha123");
    }

    public Avaliador cadastrar(String cpf, String nome, String endereco, String senhaPlana) {
        if (cpf == null || cpf.isBlank())         throw new RuntimeException("CPF inválido.");
        if (nome == null || nome.isBlank())        throw new RuntimeException("Nome inválido.");
        if (endereco == null || endereco.isBlank()) throw new RuntimeException("Endereço inválido.");
        if (avaliadorDAO.buscarPorCpf(cpf) != null) throw new RuntimeException("Avaliador já cadastrado com esse CPF.");

        Usuario u = usuarioDAO.buscarPorCpf(cpf);
        if (u == null) {
            u = new Usuario(cpf, nome, endereco, SenhaUtil.hash(senhaPlana));
            usuarioDAO.inserir(u);
        }
        Avaliador avaliador = UsuarioFactory.criarAvaliador(u);
        avaliadorDAO.inserir(avaliador);
        return avaliador;
    }

    public Avaliador buscarPorCpf(String cpf)  { return avaliadorDAO.buscarPorCpf(cpf); }
    public Avaliador buscarPorNome(String nome) { return avaliadorDAO.buscarPorNome(nome); }
    public List<Avaliador> listarTodos()        { return avaliadorDAO.listarTodos(); }

    public void atualizar(String cpf, String novoNome, String novoEndereco) {
        if (avaliadorDAO.buscarPorCpf(cpf) == null) throw new RuntimeException("Avaliador não encontrado.");
        usuarioDAO.atualizar(cpf, novoNome, novoEndereco);
    }

    public void excluir(String cpf) {
        if (avaliadorDAO.buscarPorCpf(cpf) == null) throw new RuntimeException("Avaliador não encontrado.");
        avaliadorDAO.excluir(cpf);
        if (usuarioDAO.detectarTipo(cpf) == null) usuarioDAO.excluir(cpf);
    }
}
