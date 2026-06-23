package br.org.editora.model.DAO;

import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection con() { return ConnectionFactory.getConnection(); }

    public Usuario inserir(Usuario u) {
        String sql = "INSERT INTO usuario (cpf, nome, endereco) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, u.getCpf());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getEndereco());
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
        return u;
    }

    public Usuario buscarPorCpf(String cpf) {
        String sql = "SELECT cpf, nome, endereco FROM usuario WHERE cpf = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Usuario buscarPorNome(String nome) {
        String sql = "SELECT cpf, nome, endereco FROM usuario WHERE nome = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT cpf, nome, endereco FROM usuario";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco")));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizar(String cpf, String novoNome, String novoEndereco) {
        String sql = "UPDATE usuario SET nome = ?, endereco = ? WHERE cpf = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, novoNome);
            ps.setString(2, novoEndereco);
            ps.setString(3, cpf);
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public void atualizarNome(String cpf, String novoNome) {
        String sql = "UPDATE usuario SET nome = ? WHERE cpf = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, novoNome);
            ps.setString(2, cpf);
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public void atualizarEndereco(String cpf, String novoEndereco) {
        String sql = "UPDATE usuario SET endereco = ? WHERE cpf = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, novoEndereco);
            ps.setString(2, cpf);
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public void excluir(String cpf) {
        String sql = "DELETE FROM usuario WHERE cpf = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public String detectarTipo(String cpf) {
        String[] tabelas = {"gerente", "avaliador", "autor"};
        String[] tipos   = {"GERENTE", "AVALIADOR", "AUTOR"};
        for (int i = 0; i < tabelas.length; i++) {
            String sql = "SELECT 1 FROM " + tabelas[i] + " WHERE cpf_usuario = ?";
            try (PreparedStatement ps = con().prepareStatement(sql)) {
                ps.setString(1, cpf);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) return tipos[i];
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }
}
