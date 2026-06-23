package br.org.editora.model.DAO;

import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    private Connection con() { return ConnectionFactory.getConnection(); }

    public Autor inserir(Autor autor) {
        String sql = "INSERT INTO autor (cpf_usuario) VALUES (?)";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, autor.getautor().getCpf());
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
        return autor;
    }

    public Autor buscarPorCpf(String cpf) {
        String sql = "SELECT u.cpf, u.nome, u.endereco FROM autor a JOIN usuario u ON a.cpf_usuario=u.cpf WHERE a.cpf_usuario=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Autor(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco")));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Autor buscarPorNome(String nome) {
        String sql = "SELECT u.cpf, u.nome, u.endereco FROM autor a JOIN usuario u ON a.cpf_usuario=u.cpf WHERE u.nome=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Autor(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco")));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Autor> listarTodos() {
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco FROM autor a JOIN usuario u ON a.cpf_usuario=u.cpf ORDER BY u.nome";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(new Autor(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"))));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void excluir(String cpf) {
        String sql = "DELETE FROM autor WHERE cpf_usuario=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf); ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
}
