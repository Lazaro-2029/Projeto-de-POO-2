package br.org.editora.model.service;

import br.org.editora.model.DAO.AutorDAO;
import br.org.editora.model.DAO.AvaliadorDAO;
import br.org.editora.model.DAO.ObraDAO;
import br.org.editora.model.entities.Autor;
import br.org.editora.model.entities.Avaliador;
import br.org.editora.model.entities.Obra;
import java.time.LocalDate;
import java.util.List;

public class ObraService {
    private final ObraDAO obraDAO = new ObraDAO();
    private final AutorDAO autorDAO = new AutorDAO();
    private final AvaliadorDAO avaliadorDAO = new AvaliadorDAO();

    public int cadastrar(String titulo, String genero, int ano, String cpfAutor) {
        if (titulo == null || titulo.isBlank())   throw new RuntimeException("Título inválido.");
        if (genero == null || genero.isBlank())   throw new RuntimeException("Gênero inválido.");
        if (ano <= 0)                              throw new RuntimeException("Ano inválido.");
        Autor autor = autorDAO.buscarPorCpf(cpfAutor);
        if (autor == null) throw new RuntimeException("Autor não encontrado.");

        Obra obra = new Obra(titulo, genero, ano, autor);
        return obraDAO.inserir(obra);
    }

    public Obra buscarPorId(int id) {
        return obraDAO.buscarPorId(id);
    }

    public List<Obra> buscarPorTitulo(String titulo) {
        return obraDAO.buscarPorTitulo(titulo);
    }

    public List<Obra> buscarPorStatus(String status) {
        return obraDAO.buscarPorStatus(status);
    }

    public List<Obra> buscarPorAutorNome(String nome) {
        return obraDAO.buscarPorAutorNome(nome);
    }

    public List<Obra> buscarPorAno(int ano) {
        return obraDAO.buscarPorAno(ano);
    }

    public List<Obra> buscarPorAvaliadorCpf(String cpf) {
        return obraDAO.buscarPorAvaliadorCpf(cpf);
    }

    public List<Obra> buscarPorAutorCpf(String cpf) {
        return obraDAO.buscarPorAutorCpf(cpf);
    }

    public List<Obra> listarTodas() {
        return obraDAO.listarTodas();
    }

    public void atualizar(int idBanco, String titulo, String genero, int ano) {
        if (titulo == null || titulo.isBlank()) throw new RuntimeException("Título inválido.");
        if (genero == null || genero.isBlank()) throw new RuntimeException("Gênero inválido.");
        if (ano <= 0)                            throw new RuntimeException("Ano inválido.");
        obraDAO.atualizar(idBanco, titulo, genero, ano);
    }

    public void atribuirAvaliador(int idObra, String cpfAvaliador) {
        Avaliador avaliador = avaliadorDAO.buscarPorCpf(cpfAvaliador);
        if (avaliador == null) throw new RuntimeException("Avaliador não encontrado.");
        obraDAO.atribuirAvaliador(idObra, cpfAvaliador);
    }

    public void avaliar(int idObra, String cpfAvaliador, boolean aprovar) {
        Obra obra = obraDAO.buscarPorId(idObra);
        if (obra == null) throw new RuntimeException("Obra não encontrada.");
        if (!"PENDENTE".equals(obra.getStatus())) throw new RuntimeException("Esta obra já foi avaliada.");
        if (obra.getAvaliadorResponsavel() == null ||
            !obra.getAvaliadorResponsavel().getavaliador().getCpf().equals(cpfAvaliador))
            throw new RuntimeException("Você não é o avaliador responsável por esta obra.");

        String novoStatus = aprovar ? "APROVADO" : "REPROVADO";
        obraDAO.atualizarStatus(idObra, novoStatus, LocalDate.now());
    }

    public void excluir(int idObra) {
        obraDAO.excluir(idObra);
    }
}
