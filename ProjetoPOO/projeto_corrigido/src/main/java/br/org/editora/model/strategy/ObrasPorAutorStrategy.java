package br.org.editora.model.strategy;

import br.org.editora.model.DAO.RelatorioDAO;
import java.util.List;

public class ObrasPorAutorStrategy implements RelatorioStrategy {
    private final RelatorioDAO dao = new RelatorioDAO();

    @Override
    public List<Object[]> gerar() {
        return dao.obrasPorAutor();
    }
}
