package br.org.editora.model.DAO;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliadorDAO {
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

    public Avaliador inserir(Avaliador avaliador) {
        con = getConnection();
        String sql = "INSERT INTO avaliador (cpf_usuario) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, avaliador.getavaliador().getCpf());
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return avaliador;
    }

    public Avaliador buscarPorCpf(String cpf) {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM avaliador av "
                   + "JOIN usuario u ON av.cpf_usuario = u.cpf "
                   + "WHERE av.cpf_usuario = ?";
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
                return new Avaliador(usuario);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Avaliador buscarPorNome(String nome) {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM avaliador av "
                   + "JOIN usuario u ON av.cpf_usuario = u.cpf "
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
                return new Avaliador(usuario);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Avaliador> listarTodos() {
        con = getConnection();
        List<Avaliador> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM avaliador av "
                   + "JOIN usuario u ON av.cpf_usuario = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                lista.add(new Avaliador(usuario));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void excluir(String cpf) {
        con = getConnection();
        String sql = "DELETE FROM avaliador WHERE cpf_usuario = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
