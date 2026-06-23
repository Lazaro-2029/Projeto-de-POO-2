package br.org.editora.model.DAO;

import br.org.editora.model.entities.Gerente;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenteDAO {
    private Connection con() { return ConnectionFactory.getConnection(); }

    public Gerente inserir(Gerente gerente) {
        String sql = "INSERT INTO gerente (cpf_usuario) VALUES (?)";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, gerente.getGerente().getCpf()); ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
        return gerente;
    }

    public Gerente buscarPorCpf(String cpf) {
        String sql = "SELECT u.cpf, u.nome, u.endereco FROM gerente g JOIN usuario u ON g.cpf_usuario=u.cpf WHERE g.cpf_usuario=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Gerente(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco")));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Gerente buscarPorNome(String nome) {
        String sql = "SELECT u.cpf, u.nome, u.endereco FROM gerente g JOIN usuario u ON g.cpf_usuario=u.cpf WHERE u.nome=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Gerente(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco")));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Gerente> listarTodos() {
        List<Gerente> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco FROM gerente g JOIN usuario u ON g.cpf_usuario=u.cpf";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(new Gerente(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"))));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void excluir(String cpf) {
        String sql = "DELETE FROM gerente WHERE cpf_usuario=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf); ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
}
