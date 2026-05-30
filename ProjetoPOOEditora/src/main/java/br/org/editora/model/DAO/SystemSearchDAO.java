package br.org.editora.model.DAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SystemSearchDAO {
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

    public Obra buscarObraPorId(int id) {
        con = getConnection();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE o.id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Obra obra = construirObra(rs);
                ps.close();
                return obra;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void buscarObraPorTitulo(String titulo) {
        con = getConnection();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE o.titulo = ?";
        boolean encontrou = false;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                construirObra(rs).exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        if (!encontrou) System.out.println("Nenhuma obra encontrada com esse título.");
    }

    public void buscarObraPorAno(int ano) {
        con = getConnection();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE o.ano = ?";
        boolean encontrou = false;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ano);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                construirObra(rs).exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        if (!encontrou) System.out.println("Nenhuma obra encontrada nesse ano.");
    }

    public void buscarObraPorStatus(String status) {
        con = getConnection();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE o.status = ?";
        boolean encontrou = false;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                construirObra(rs).exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        if (!encontrou) System.out.println("Nenhuma obra encontrada com esse status.");
    }

    public void buscarObrasPorAutor(String nomeAutor) {
        con = getConnection();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE u.nome = ?";
        boolean encontrou = false;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nomeAutor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                construirObra(rs).exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        if (!encontrou) System.out.println("Nenhuma obra encontrada para esse autor.");
    }

    public Autor buscarAutorPorNome(String nome) {
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
                Usuario u = new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                ps.close();
                return new Autor(u);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public Avaliador buscarAvaliadorPorNome(String nome) {
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
                Usuario u = new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                );
                ps.close();
                return new Avaliador(u);
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void listarTodasObras() {
        con = getConnection();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean encontrou = false;
            while (rs.next()) {
                construirObra(rs).exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
            if (!encontrou) System.out.println("Nenhuma obra listada no sistema.");
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void listarAutores() {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM autor a "
                   + "JOIN usuario u ON a.cpf_usuario = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean encontrou = false;
            while (rs.next()) {
                System.out.println("Nome: "     + rs.getString("nome"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("CPF: "      + rs.getString("cpf"));
                System.out.println("-------------");
                encontrou = true;
            }
            if (!encontrou) System.out.println("Nenhum autor listado no sistema.");
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void listarAvaliadores() {
        con = getConnection();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM avaliador av "
                   + "JOIN usuario u ON av.cpf_usuario = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            boolean encontrou = false;
            while (rs.next()) {
                System.out.println("Nome: "     + rs.getString("nome"));
                System.out.println("Endereço: " + rs.getString("endereco"));
                System.out.println("CPF: "      + rs.getString("cpf"));
                System.out.println("-------------");
                encontrou = true;
            }
            if (!encontrou) System.out.println("Nenhum avaliador listado no sistema.");
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Obra> getObras() {
        con = getConnection();
        List<Obra> lista = new ArrayList<>();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(construirObra(rs));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Autor> getAutores() {
        con = getConnection();
        List<Autor> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM autor a "
                   + "JOIN usuario u ON a.cpf_usuario = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Autor(new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                )));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Avaliador> getAvaliadores() {
        con = getConnection();
        List<Avaliador> lista = new ArrayList<>();
        String sql = "SELECT u.cpf, u.nome, u.endereco "
                   + "FROM avaliador av "
                   + "JOIN usuario u ON av.cpf_usuario = u.cpf";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Avaliador(new Usuario(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("endereco")
                )));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    private Obra construirObra(ResultSet rs) throws SQLException {
        Usuario usuarioAutor = new Usuario(
            rs.getString("cpf_autor"),
            rs.getString("nome_autor"),
            rs.getString("end_autor")
        );
        Obra obra = new Obra(
            rs.getString("titulo"),
            rs.getString("genero"),
            rs.getInt   ("ano"),
            new Autor(usuarioAutor)
        );
        obra.alterarStatus(rs.getString("status"));
        Date dataAvaliacao = rs.getDate("data_avaliacao");
        if (dataAvaliacao != null) {
            obra.setDataAvaliacao(dataAvaliacao.toLocalDate());
        }
        return obra;
    }
}
