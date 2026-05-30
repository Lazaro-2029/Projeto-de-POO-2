package br.org.editora.model.DAO;
import java.sql.*;

public class RelatorioDAO {
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

    public int totalObras() {
        con = getConnection();
        String sql = "SELECT COUNT(*) AS total FROM obra";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                ps.close();
                return total;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int totalAutores() {
        con = getConnection();
        String sql = "SELECT COUNT(*) AS total FROM autor";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                ps.close();
                return total;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int totalAvaliadores() {
        con = getConnection();
        String sql = "SELECT COUNT(*) AS total FROM avaliador";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                ps.close();
                return total;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public void relatorioStatusObras() {
        con = getConnection();
        String sql = "SELECT status, COUNT(*) AS quantidade FROM obra GROUP BY status";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("=== Status das Obras ===");
            while (rs.next()) {
                System.out.println(rs.getString("status") + ": " + rs.getInt("quantidade"));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void relatorioObrasPorAutor() {
        con = getConnection();
        String sql = "SELECT u.nome AS nome_autor, COUNT(o.id) AS quantidade "
                   + "FROM autor a "
                   + "JOIN usuario u ON a.cpf_usuario = u.cpf "
                   + "LEFT JOIN obra o ON o.cpf_autor = a.cpf_usuario "
                   + "GROUP BY a.cpf_usuario, u.nome";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("=== Obras por Autor ===");
            while (rs.next()) {
                System.out.println("Autor: " + rs.getString("nome_autor")
                        + " | Obras: " + rs.getInt("quantidade"));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void relatorioObrasPorAvaliador() {
        con = getConnection();
        String sql = "SELECT u.nome AS nome_avaliador, COUNT(o.id) AS quantidade "
                   + "FROM avaliador av "
                   + "JOIN usuario u ON av.cpf_usuario = u.cpf "
                   + "LEFT JOIN obra o ON o.cpf_avaliador = av.cpf_usuario "
                   + "GROUP BY av.cpf_usuario, u.nome";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("=== Obras por Avaliador ===");
            while (rs.next()) {
                System.out.println("Avaliador: " + rs.getString("nome_avaliador")
                        + " | Obras avaliadas: " + rs.getInt("quantidade"));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void gerarRelatorioGeral() {
        System.out.println("===== RELATÓRIO GERAL DO SISTEMA =====");
        System.out.println("Total de obras: "       + totalObras());
        System.out.println("Total de autores: "     + totalAutores());
        System.out.println("Total de avaliadores: " + totalAvaliadores());
        System.out.println("-------------------");
        relatorioStatusObras();
        System.out.println("-------------------");
        relatorioObrasPorAutor();
        System.out.println("-------------------");
        relatorioObrasPorAvaliador();
        System.out.println("=======================================");
    }
}
