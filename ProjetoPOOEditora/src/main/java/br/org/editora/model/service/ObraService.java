package br.org.editora.model.service;

import br.org.editora.exceptions.SemAutorException;
import br.org.editora.exceptions.SemGeneroException;
import br.org.editora.exceptions.SemTituloException;
import br.org.editora.model.DAO.ObraDAO;
import br.org.editora.model.entities.Obra;

import java.util.List;

public class ObraService {

    private ObraDAO obraDao = new ObraDAO();

    public Obra submeter(Obra obra)
            throws SemTituloException,
            SemGeneroException,
            SemAutorException {

        if (obra.getTitulo() == null || obra.getTitulo().isEmpty()) {
            throw new SemTituloException("Título inválido!");
        }

        if (obra.getGenero() == null || obra.getGenero().isEmpty()) {
            throw new SemGeneroException("Gênero inválido!");
        }

        if (obra.getAno() <= 0) {
            throw new IllegalArgumentException("Ano inválido!");
        }

        if (obra.getAutor() == null) {
            throw new SemAutorException("Autor  inválido!");
        }

        verificar(obra);

        return obraDao.inserir(obra);
    }

    private void verificar(Obra obra) {

        List<Obra> obras =
                obraDao.buscarPorTitulo(obra.getTitulo());

        for (Obra o : obras) {

            if (o.getAutor().getautor().getCpf()
                    .equals(
                            obra.getAutor().getautor().getCpf())) {

                throw new IllegalArgumentException(
                        "Essa obra já existe para esse autor!"
                );
            }
        }
    }
}