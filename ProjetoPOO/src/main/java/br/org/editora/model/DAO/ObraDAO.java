package br.org.editora.model.DAO;

import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ObraDAO {
    private Connection con() { return ConnectionFactory.getConnection(); }

    public int inserir(Obra obra) {
        String sql = "INSERT INTO obra (titulo, genero, ano, status, data_avaliacao, cpf_autor, cpf_avaliador) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, obra.getTitulo());
            ps.setString(2, obra.getGenero());
            ps.setInt(3, obra.getAno());
            ps.setString(4, obra.getStatus());
            if (obra.getDataAvaliacao() != null) ps.setDate(5, Date.valueOf(obra.getDataAvaliacao()));
            else ps.setNull(5, Types.DATE);
            ps.setString(6, obra.getAutor().getautor().getCpf());
            if (obra.getAvaliadorResponsavel() != null) ps.setString(7, obra.getAvaliadorResponsavel().getavaliador().getCpf());
            else ps.setNull(7, Types.VARCHAR);
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
        return -1;
    }

    public Obra buscarPorId(int id) {
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor, "
                   + "av.cpf_usuario AS cpf_avaliador, uav.nome AS nome_avaliador, uav.endereco AS end_avaliador "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "LEFT JOIN avaliador av ON o.cpf_avaliador = av.cpf_usuario "
                   + "LEFT JOIN usuario uav ON av.cpf_usuario = uav.cpf "
                   + "WHERE o.id = ?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return construirObra(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<Obra> buscarPorTitulo(String titulo) {
        return buscarComFiltro("LOWER(o.titulo) LIKE LOWER(?)", "%" + titulo + "%");
    }

    public List<Obra> buscarPorStatus(String status) {
        return buscarComFiltroExato("o.status = ?", status.toUpperCase());
    }

    public List<Obra> buscarPorAutorCpf(String cpfAutor) {
        return buscarComFiltroExato("o.cpf_autor = ?", cpfAutor);
    }

    public List<Obra> buscarPorAno(int ano) {
        String sql = sqlBase() + " WHERE o.ano = ? ORDER BY o.titulo";
        List<Obra> lista = new ArrayList<>();
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setInt(1, ano);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(construirObra(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Obra> buscarPorAutorNome(String nomeAutor) {
        return buscarComFiltro("LOWER(u.nome) LIKE LOWER(?)", "%" + nomeAutor + "%");
    }

    public List<Obra> buscarPorAvaliadorCpf(String cpfAvaliador) {
        return buscarComFiltroExato("o.cpf_avaliador = ?", cpfAvaliador);
    }

    public List<Obra> listarTodas() {
        List<Obra> lista = new ArrayList<>();
        String sql = sqlBase() + " ORDER BY o.titulo";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(construirObra(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Obra> listarPorPeriodo(LocalDate inicio, LocalDate fim) {
        List<Obra> lista = new ArrayList<>();
        String sql = sqlBase() + " WHERE o.data_avaliacao BETWEEN ? AND ? AND o.status != 'PENDENTE' ORDER BY o.data_avaliacao";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fim));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(construirObra(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public void atualizar(int id, String titulo, String genero, int ano) {
        String sql = "UPDATE obra SET titulo=?, genero=?, ano=? WHERE id=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, titulo); ps.setString(2, genero); ps.setInt(3, ano); ps.setInt(4, id);
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public void atualizarStatus(int id, String novoStatus, LocalDate dataAvaliacao) {
        String sql = "UPDATE obra SET status=?, data_avaliacao=? WHERE id=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, novoStatus.toUpperCase());
            ps.setDate(2, dataAvaliacao != null ? Date.valueOf(dataAvaliacao) : null);
            ps.setInt(3, id);
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public void atribuirAvaliador(int idObra, String cpfAvaliador) {
        String sql = "UPDATE obra SET cpf_avaliador=? WHERE id=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            if (cpfAvaliador != null) ps.setString(1, cpfAvaliador);
            else ps.setNull(1, Types.VARCHAR);
            ps.setInt(2, idObra);
            ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM obra WHERE id=?";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setInt(1, id); ps.execute();
        } catch (SQLException e) { throw new RuntimeException(e.getMessage(), e); }
    }

    private String sqlBase() {
        return "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
             + "u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor, "
             + "av.cpf_usuario AS cpf_avaliador, uav.nome AS nome_avaliador, uav.endereco AS end_avaliador "
             + "FROM obra o "
             + "JOIN usuario u ON o.cpf_autor = u.cpf "
             + "LEFT JOIN avaliador av ON o.cpf_avaliador = av.cpf_usuario "
             + "LEFT JOIN usuario uav ON av.cpf_usuario = uav.cpf";
    }

    private List<Obra> buscarComFiltro(String condicao, String valor) {
        List<Obra> lista = new ArrayList<>();
        String sql = sqlBase() + " WHERE " + condicao + " ORDER BY o.titulo";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, valor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(construirObra(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    private List<Obra> buscarComFiltroExato(String condicao, String valor) {
        List<Obra> lista = new ArrayList<>();
        String sql = sqlBase() + " WHERE " + condicao + " ORDER BY o.titulo";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setString(1, valor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(construirObra(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public Obra construirObra(ResultSet rs) throws SQLException {
        Usuario usuAutor = new Usuario(rs.getString("cpf_autor"), rs.getString("nome_autor"), rs.getString("end_autor"));
        Autor autor = new Autor(usuAutor);

        Obra obra = new Obra(rs.getString("titulo"), rs.getString("genero"), rs.getInt("ano"), autor);
        obra.setIdBanco(rs.getInt("id"));
        obra.alterarStatus(rs.getString("status"));

        Date dataAval = rs.getDate("data_avaliacao");
        if (dataAval != null) obra.setDataAvaliacao(dataAval.toLocalDate());

        String cpfAval = rs.getString("cpf_avaliador");
        if (cpfAval != null) {
            String nomeAval = rs.getString("nome_avaliador");
            String endAval  = rs.getString("end_avaliador");
            if (nomeAval != null) {
                Usuario usuAval = new Usuario(cpfAval, nomeAval, endAval != null ? endAval : "");
                obra.setAvaliadorResponsavel(new Avaliador(usuAval));
            }
        }
        return obra;
    }
}
