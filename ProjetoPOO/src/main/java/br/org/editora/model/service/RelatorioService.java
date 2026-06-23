package br.org.editora.model.service;

import br.org.editora.model.DAO.RelatorioDAO;
import br.org.editora.model.entities.Obra;
import java.time.LocalDate;
import java.util.List;

public class RelatorioService {
    private final RelatorioDAO dao = new RelatorioDAO();

    public int totalObras()       { return dao.totalObras(); }
    public int totalAutores()     { return dao.totalAutores(); }
    public int totalAvaliadores() { return dao.totalAvaliadores(); }

    public List<Obra> obrasAvaliadasNoPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) throw new RuntimeException("Datas inválidas.");
        if (inicio.isAfter(fim)) throw new RuntimeException("Data inicial deve ser anterior à data final.");
        return dao.obrasAvaliadasNoPeriodo(inicio, fim);
    }

    public List<Object[]> statusObras()         { return dao.statusObras(); }
    public List<Object[]> obrasPorAutor()       { return dao.obrasPorAutor(); }
    public List<Object[]> obrasPorAvaliador()   { return dao.obrasPorAvaliador(); }
}
