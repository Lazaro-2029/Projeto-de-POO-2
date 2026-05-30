package br.org.editora.model.DAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {
    private final static String URL  = "jdbc:mysql://localhost/projetopoo";
    private final static String USER = "root";
    private final static String PASS = "#Projeto21";
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

    public Autor inserir(Autor autor) {
        con = getConnection();
        String sql = "INSERT INTO autor (cpf_usuario) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, autor.getautor().getCpf());
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return autor;
    }

    public Autor buscarPorCpf(String cpf) {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                + "FROM autor a "
                + "JOIN usuario u ON a.cpf_usuario = u.cpf "
                + "WHERE a.cpf_usuario = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("endereco")
                );
                ps.close();
                return new Autor(usuario);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Autor buscarPorNome(String nome) {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                + "FROM autor a "
                + "JOIN usuario u ON a.cpf_usuario = u.cpf "
                + "WHERE u.nome = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("endereco")
                );
                ps.close();
                return new Autor(usuario);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Autor> listarTodos() {
        con = getConnection();
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                + "FROM autor a "
                + "JOIN usuario u ON a.cpf_usuario = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("endereco")
                );
                lista.add(new Autor(usuario));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void excluir(String cpf) {
        con = getConnection();
        String sql = "DELETE FROM autor WHERE cpf_usuario = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
