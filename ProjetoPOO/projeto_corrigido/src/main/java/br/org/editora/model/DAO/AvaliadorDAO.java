package br.org.editora.model.DAO;

import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliadorDAO {
    private Connection con() { return ConnectionFactory.getConnection(); }

    public Avaliador inserir(Avaliador avaliador) {
        String sql = "INSERT INTO avaliador (cpf_usuario) VALUES (?)";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, avaliador.getavaliador().getCpf()); ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
        return avaliador;
    }

    public Avaliador buscarPorCpf(String cpf) {
        String sql = "SELECT u.cpf, u.nome, u.endereco, u.senha_hash FROM avaliador av JOIN usuario u ON av.cpf_usuario=u.cpf WHERE av.cpf_usuario=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Avaliador(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"), rs.getString("senha_hash")));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Avaliador buscarPorNome(String nome) {
        String sql = "SELECT u.cpf, u.nome, u.endereco, u.senha_hash FROM avaliador av JOIN usuario u ON av.cpf_usuario=u.cpf WHERE u.nome=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new Avaliador(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"), rs.getString("senha_hash")));
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Avaliador> listarTodos() {
        List<Avaliador> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco, u.senha_hash FROM avaliador av JOIN usuario u ON av.cpf_usuario=u.cpf ORDER BY u.nome";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(new Avaliador(new Usuario(rs.getString("cpf"), rs.getString("nome"), rs.getString("endereco"), rs.getString("senha_hash"))));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void excluir(String cpf) {
        String sql = "DELETE FROM avaliador WHERE cpf_usuario=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, cpf); ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }
}
