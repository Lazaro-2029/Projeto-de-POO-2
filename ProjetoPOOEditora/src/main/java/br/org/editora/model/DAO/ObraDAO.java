package br.org.editora.model.DAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO {
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

    public Obra inserir(Obra obra) {
        con = getConnection();
        String sql = "INSERT INTO obra (titulo, genero, ano, status, data_avaliacao, cpf_autor, cpf_avaliador) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, obra.getTitulo());
            ps.setString(2, obra.getGenero());
            ps.setInt   (3, obra.getAno());
            ps.setString(4, obra.getStatus());

            if (obra.getDataAvaliacao() != null) {
                ps.setDate(5, Date.valueOf(obra.getDataAvaliacao()));
            } else {
                ps.setNull(5, Types.DATE);
            }

            ps.setString(6, obra.getAutor().getautor().getCpf());

            if (obra.getAvaliadorResponsavel() != null) {
                ps.setString(7, obra.getAvaliadorResponsavel().getavaliador().getCpf());
            } else {
                ps.setNull(7, Types.VARCHAR);
            }

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                // o id gerado pelo banco substitui o contador local da entidade
                // (como Obra.id é private final, não há setter – apenas registramos)
                System.out.println("Obra inserida com ID gerado: " + rs.getInt(1));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return obra;
    }

    public Obra buscarPorId(int id) {
        con = getConnection();
        String sql = "SELECT o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
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

    public List<Obra> buscarPorTitulo(String titulo) {
        con = getConnection();
        List<Obra> lista = new ArrayList<>();
        String sql = "SELECT o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE o.titulo = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(construirObra(rs));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Obra> buscarPorStatus(String status) {
        con = getConnection();
        List<Obra> lista = new ArrayList<>();
        String sql = "SELECT o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE o.status = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(construirObra(rs));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Obra> buscarPorAutor(String cpfAutor) {
        con = getConnection();
        List<Obra> lista = new ArrayList<>();
        String sql = "SELECT o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "       u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "WHERE o.cpf_autor = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpfAutor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(construirObra(rs));
            }
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Obra> listarTodas() {
        con = getConnection();
        List<Obra> lista = new ArrayList<>();
        String sql = "SELECT o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
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

    public void atualizarStatus(int id, String novoStatus, LocalDate dataAvaliacao) {
        con = getConnection();
        String sql = "UPDATE obra SET status = ?, data_avaliacao = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, novoStatus.toUpperCase());
            ps.setDate  (2, dataAvaliacao != null ? Date.valueOf(dataAvaliacao) : null);
            ps.setInt   (3, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void atribuirAvaliador(int idObra, String cpfAvaliador) {
        con = getConnection();
        String sql = "UPDATE obra SET cpf_avaliador = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cpfAvaliador);
            ps.setInt   (2, idObra);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void atualizar(int id, String titulo, String genero, int ano) {
        con = getConnection();
        String sql = "UPDATE obra SET titulo = ?, genero = ?, ano = ? WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, genero);
            ps.setInt   (3, ano);
            ps.setInt   (4, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void excluir(int id) {
        con = getConnection();
        String sql = "DELETE FROM obra WHERE id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private Obra construirObra(ResultSet rs) throws SQLException {
        Usuario usuarioAutor = new Usuario(
            rs.getString("cpf_autor"),
            rs.getString("nome_autor"),
            rs.getString("end_autor")
        );
        Autor autor = new Autor(usuarioAutor);

        Obra obra = new Obra(
            rs.getString("titulo"),
            rs.getString("genero"),
            rs.getInt   ("ano"),
            autor
        );

        obra.alterarStatus(rs.getString("status"));

        Date dataAvaliacao = rs.getDate("data_avaliacao");
        if (dataAvaliacao != null) {
            obra.setDataAvaliacao(dataAvaliacao.toLocalDate());
        }

        return obra;
    }
}
