package br.org.editora.model.DAO;

import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Obra;
import br.org.editora.model.entities.Usuario;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {
    private Connection con() { return ConnectionFactory.getConnection(); }

    public int totalObras()       { return contar("SELECT COUNT(*) FROM obra"); }
    public int totalAutores()     { return contar("SELECT COUNT(*) FROM autor"); }
    public int totalAvaliadores() { return contar("SELECT COUNT(*) FROM avaliador"); }

    private int contar(String sql) {
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public List<Obra> obrasAvaliadasNoPeriodo(LocalDate inicio, LocalDate fim) {
        List<Obra> lista = new ArrayList<>();
        String sql = "SELECT o.id, o.titulo, o.genero, o.ano, o.status, o.data_avaliacao, "
                   + "u.cpf AS cpf_autor, u.nome AS nome_autor, u.endereco AS end_autor, "
                   + "av.cpf_usuario AS cpf_avaliador, uav.nome AS nome_avaliador, uav.endereco AS end_avaliador "
                   + "FROM obra o "
                   + "JOIN usuario u ON o.cpf_autor = u.cpf "
                   + "LEFT JOIN avaliador av ON o.cpf_avaliador = av.cpf_usuario "
                   + "LEFT JOIN usuario uav ON av.cpf_usuario = uav.cpf "
                   + "WHERE o.status <> 'PENDENTE' AND o.data_avaliacao BETWEEN ? AND ? "
                   + "ORDER BY o.data_avaliacao";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(inicio));
            ps.setDate(2, Date.valueOf(fim));
            ResultSet rs = ps.executeQuery();
            ObraDAO obraDAO = new ObraDAO();
            while (rs.next()) lista.add(obraDAO.construirObra(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    public List<Object[]> statusObras() {
        List<Object[]> resultado = new ArrayList<>();
        String sql = "SELECT status, COUNT(*) AS qtd FROM obra GROUP BY status";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) resultado.add(new Object[]{ rs.getString("status"), rs.getInt("qtd") });
        } catch (SQLException e) { e.printStackTrace(); }
        return resultado;
    }

    public List<Object[]> obrasPorAutor() {
        List<Object[]> resultado = new ArrayList<>();
        String sql = "SELECT u.nome, COUNT(o.id) AS qtd FROM autor a JOIN usuario u ON a.cpf_usuario=u.cpf "
                   + "LEFT JOIN obra o ON o.cpf_autor=a.cpf_usuario GROUP BY a.cpf_usuario, u.nome ORDER BY u.nome";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) resultado.add(new Object[]{ rs.getString("nome"), rs.getInt("qtd") });
        } catch (SQLException e) { e.printStackTrace(); }
        return resultado;
    }

    public List<Object[]> obrasPorAvaliador() {
        List<Object[]> resultado = new ArrayList<>();
        String sql = "SELECT u.nome, COUNT(o.id) AS qtd FROM avaliador av JOIN usuario u ON av.cpf_usuario=u.cpf "
                   + "LEFT JOIN obra o ON o.cpf_avaliador=av.cpf_usuario AND o.status<>'PENDENTE' "
                   + "GROUP BY av.cpf_usuario, u.nome ORDER BY u.nome";
        try (PreparedStatement ps = con().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) resultado.add(new Object[]{ rs.getString("nome"), rs.getInt("qtd") });
        } catch (SQLException e) { e.printStackTrace(); }
        return resultado;
    }
}
