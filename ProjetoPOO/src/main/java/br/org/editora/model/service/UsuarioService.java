package br.org.editora.model.service;

import br.org.editora.model.DAO.AutorDAO;
import br.org.editora.model.DAO.AvaliadorDAO;
import br.org.editora.model.DAO.GerenteDAO;
import br.org.editora.model.DAO.UsuarioDAO;
import br.org.editora.model.entities.Usuario;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();
    private final AutorDAO autorDAO = new AutorDAO();
    private final AvaliadorDAO avaliadorDAO = new AvaliadorDAO();
    private final GerenteDAO gerenteDAO = new GerenteDAO();

    public String[] autenticar(String cpf) {
        Usuario u = dao.buscarPorCpf(cpf);
        if (u == null) throw new RuntimeException("Usuário não encontrado.");
        String tipo = dao.detectarTipo(cpf);
        if (tipo == null) throw new RuntimeException("Usuário sem perfil definido.");
        return new String[]{ u.getNome(), tipo };
    }

    public Usuario cadastrar(String cpf, String nome, String endereco) {
        if (cpf == null || cpf.isBlank())       throw new RuntimeException("CPF inválido.");
        if (nome == null || nome.isBlank())      throw new RuntimeException("Nome inválido.");
        if (endereco == null || endereco.isBlank()) throw new RuntimeException("Endereço inválido.");
        if (dao.buscarPorCpf(cpf) != null)      throw new RuntimeException("CPF já cadastrado.");
        Usuario u = new Usuario(cpf, nome, endereco);
        return dao.inserir(u);
    }

    public Usuario buscarPorCpf(String cpf) {
        return dao.buscarPorCpf(cpf);
    }

    public void atualizar(String cpf, String novoNome, String novoEndereco) {
        if (dao.buscarPorCpf(cpf) == null) throw new RuntimeException("Usuário não encontrado.");
        dao.atualizar(cpf, novoNome, novoEndereco);
    }

    public void excluir(String cpf) {
        if (dao.buscarPorCpf(cpf) == null) throw new RuntimeException("Usuário não encontrado.");
        dao.excluir(cpf);
    }
}
