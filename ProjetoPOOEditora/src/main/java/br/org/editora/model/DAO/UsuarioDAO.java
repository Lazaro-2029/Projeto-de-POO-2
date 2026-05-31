package br.org.editora.model.DAO;
import br.org.editora.exceptions.SemCPFException;
import br.org.editora.exceptions.SemEnderecoException;
import br.org.editora.exceptions.SemNomeException;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final static String URL  = "jdbc:mysql://localhost/projetopoo";
    private final static String USER = "root";
    private final static String PASS = "1234";
    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e) { e.printStackTrace(); }
        }
        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public Usuario inserir(Usuario entity) {
        con = getConnection();
        String sql = "INSERT INTO usuario (cpf, nome, endereco) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, entity.getCpf());
            ps.setString(2, entity.getNome());
            ps.setString(3, entity.getEndereco());
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public Usuario buscarPorCpf(String cpf) {
        con = getConnection();
        String sql = "SELECT cpf, nome, endereco FROM usuario WHERE cpf = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                ps.close();
                return u;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Usuario buscarPorNome(String nome) {
        con = getConnection();
        String sql = "SELECT cpf, nome, endereco FROM usuario WHERE nome = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                ps.close();
                return u;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Usuario> listarTodos() {
        con = getConnection();
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT cpf, nome, endereco FROM usuario";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                ));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizarNome(String cpf, String novoNome) {
        con = getConnection();
        String sql = "UPDATE usuario SET nome = ? WHERE cpf = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novoNome);
            ps.setString(2, cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void atualizarEndereco(String cpf, String novoEndereco) {
        con = getConnection();
        String sql = "UPDATE usuario SET endereco = ? WHERE cpf = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novoEndereco);
            ps.setString(2, cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void excluir(String cpf) {
        con = getConnection();
        String sql = "DELETE FROM usuario WHERE cpf = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
