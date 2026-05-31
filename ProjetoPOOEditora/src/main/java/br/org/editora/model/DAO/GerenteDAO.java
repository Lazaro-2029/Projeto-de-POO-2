package br.org.editora.model.DAO;
import br.org.editora.model.entities.Gerente;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerenteDAO {
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

    public Gerente inserir(Gerente gerente) {
        con = getConnection();
        String sql = "INSERT INTO gerente (cpf_usuario) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, gerente.getGerente().getCpf());
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return gerente;
    }

    public Gerente buscarPorCpf(String cpf) {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM gerente g "
                   + "JOIN usuario u ON g.cpf_usuario = u.cpf "
                   + "WHERE g.cpf_usuario = ?";
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
                return new Gerente(usuario);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Gerente buscarPorNome(String nome) {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM gerente g "
                   + "JOIN usuario u ON g.cpf_usuario = u.cpf "
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
                return new Gerente(usuario);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Gerente> listarTodos() {
        con = getConnection();
        List<Gerente> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM gerente g "
                   + "JOIN usuario u ON g.cpf_usuario = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                lista.add(new Gerente(usuario));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void excluir(String cpf) {
        con = getConnection();
        String sql = "DELETE FROM gerente WHERE cpf_usuario = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
