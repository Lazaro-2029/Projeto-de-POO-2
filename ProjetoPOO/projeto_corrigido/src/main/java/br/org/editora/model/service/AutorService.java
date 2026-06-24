package br.org.editora.model.service;

import br.org.editora.model.DAO.AutorDAO;
import br.org.editora.model.DAO.UsuarioDAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Usuario;
import br.org.editora.model.factory.UsuarioFactory;
import br.org.editora.model.util.SenhaUtil;
import java.util.List;

public class AutorService {
    private final AutorDAO autorDAO = new AutorDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /** Cadastro feito pelo gerente — senha padrão "senha123", o autor deve trocar depois. */
    public Autor cadastrar(String cpf, String nome, String endereco) {
        return cadastrar(cpf, nome, endereco, "senha123");
    }

    public Autor cadastrar(String cpf, String nome, String endereco, String senhaPlana) {
        if (cpf == null || cpf.isBlank())         throw new RuntimeException("CPF inválido.");
        if (nome == null || nome.isBlank())        throw new RuntimeException("Nome inválido.");
        if (endereco == null || endereco.isBlank()) throw new RuntimeException("Endereço inválido.");
        if (senhaPlana == null || senhaPlana.isBlank()) throw new RuntimeException("Senha inválida.");
        if (senhaPlana.length() < 6)               throw new RuntimeException("A senha deve ter pelo menos 6 caracteres.");
        if (autorDAO.buscarPorCpf(cpf) != null)    throw new RuntimeException("Autor já cadastrado com esse CPF.");

        Usuario u = usuarioDAO.buscarPorCpf(cpf);
        if (u == null) {
            u = new Usuario(cpf, nome, endereco, SenhaUtil.hash(senhaPlana));
            usuarioDAO.inserir(u);
        }
        Autor autor = UsuarioFactory.criarAutor(u);
        autorDAO.inserir(autor);
        return autor;
    }

    public Autor buscarPorCpf(String cpf)  { return autorDAO.buscarPorCpf(cpf); }
    public Autor buscarPorNome(String nome) { return autorDAO.buscarPorNome(nome); }
    public List<Autor> listarTodos()        { return autorDAO.listarTodos(); }

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
