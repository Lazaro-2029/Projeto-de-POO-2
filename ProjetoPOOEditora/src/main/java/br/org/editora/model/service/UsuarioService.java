package br.org.editora.model.service;

import br.org.editora.exceptions.SemCPFException;
import br.org.editora.exceptions.SemEnderecoException;
import br.org.editora.exceptions.SemNomeException;
import br.org.editora.model.DAO.UsuarioDAO;
import br.org.editora.model.entities.Usuario;

import java.util.List;

public class UsuarioService {

    private UsuarioDAO dao = new UsuarioDAO();

    public Usuario cadastrarUsuario(
            String cpf,
            String nome,
            String endereco){

        Usuario existente = dao.buscarPorCpf(cpf);

        if(existente != null){
            throw new RuntimeException(
                    "Já existe um usuário com esse CPF."
            );
        }

        try{

            Usuario usuario =
                    new Usuario(cpf,nome,endereco);

            usuario.setCpf(cpf);
            usuario.setNome(nome);
            usuario.setEndereco(endereco);

            return dao.inserir(usuario);

        }catch (SemCPFException |
                SemNomeException |
                SemEnderecoException e){

            throw new RuntimeException(e.getMessage());

        }
    }

    public Usuario buscarPorCpf(String cpf){
        return dao.buscarPorCpf(cpf);
    }

    public Usuario buscarPorNome(String nome){
        return dao.buscarPorNome(nome);
    }

    public List<Usuario> listarUsuarios(){
        return dao.listarTodos();
    }

    public void removerUsuario(String cpf){

        Usuario usuario = dao.buscarPorCpf(cpf);

        if(usuario == null){
            throw new RuntimeException(
                    "Usuário não encontrado."
            );
        }

        dao.excluir(cpf);
    }

    public void alterarNome(
            String cpf,
            String novoNome){

        Usuario usuario = dao.buscarPorCpf(cpf);

        if(usuario == null){
            throw new RuntimeException(
                    "Usuário não encontrado."
            );
        }

        try{
            usuario.setNome(novoNome);
            dao.atualizarNome(cpf, novoNome);

        }catch (SemNomeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void alterarEndereco(
            String cpf,
            String novoEndereco){

        Usuario usuario = dao.buscarPorCpf(cpf);

        if(usuario == null){
            throw new RuntimeException(
                    "Usuário não encontrado."
            );
        }

        try{
            usuario.setEndereco(novoEndereco);
            dao.atualizarEndereco(cpf, novoEndereco);

        }catch (SemEnderecoException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}