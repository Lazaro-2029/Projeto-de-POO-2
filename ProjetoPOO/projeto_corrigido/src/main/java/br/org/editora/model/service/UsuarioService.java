package br.org.editora.model.service;

import br.org.editora.model.DAO.UsuarioDAO;
import br.org.editora.model.entities.Usuario;
import br.org.editora.model.util.SenhaUtil;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public String[] autenticar(String cpf, String senhaPlana) {
        Usuario u = dao.buscarPorCpf(cpf);
        if (u == null) throw new RuntimeException("Usuário não encontrado.");

        if (!SenhaUtil.verificar(senhaPlana, u.getSenhaHash()))
            throw new RuntimeException("Senha incorreta.");

        String tipo = dao.detectarTipo(cpf);
        if (tipo == null) throw new RuntimeException("Usuário sem perfil definido.");

        return new String[]{ u.getNome(), tipo };
    }

    public Usuario cadastrar(String cpf, String nome, String endereco, String senhaPlana) {
        if (cpf == null || cpf.isBlank())        throw new RuntimeException("CPF inválido.");
        if (nome == null || nome.isBlank())       throw new RuntimeException("Nome inválido.");
        if (endereco == null || endereco.isBlank()) throw new RuntimeException("Endereço inválido.");
        if (senhaPlana == null || senhaPlana.isBlank()) throw new RuntimeException("Senha inválida.");
        if (senhaPlana.length() < 6)              throw new RuntimeException("A senha deve ter pelo menos 6 caracteres.");
        if (dao.buscarPorCpf(cpf) != null)        throw new RuntimeException("CPF já cadastrado.");

        String hash = SenhaUtil.hash(senhaPlana);
        Usuario u = new Usuario(cpf, nome, endereco, hash);
        return dao.inserir(u);
    }

    public Usuario buscarPorCpf(String cpf) {
        return dao.buscarPorCpf(cpf);
    }

    public void atualizar(String cpf, String novoNome, String novoEndereco) {
        if (dao.buscarPorCpf(cpf) == null) throw new RuntimeException("Usuário não encontrado.");
        dao.atualizar(cpf, novoNome, novoEndereco);
    }

    public void alterarSenha(String cpf, String senhaAtual, String novaSenha) {
        Usuario u = dao.buscarPorCpf(cpf);
        if (u == null) throw new RuntimeException("Usuário não encontrado.");
        if (!SenhaUtil.verificar(senhaAtual, u.getSenhaHash()))
            throw new RuntimeException("Senha atual incorreta.");
        if (novaSenha == null || novaSenha.length() < 6)
            throw new RuntimeException("Nova senha deve ter pelo menos 6 caracteres.");
        dao.atualizarSenha(cpf, SenhaUtil.hash(novaSenha));
    }

    public void excluir(String cpf) {
        if (dao.buscarPorCpf(cpf) == null) throw new RuntimeException("Usuário não encontrado.");
        dao.excluir(cpf);
    }
}
