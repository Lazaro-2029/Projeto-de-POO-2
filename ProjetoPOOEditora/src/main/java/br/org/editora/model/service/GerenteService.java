package br.org.editora.model.service;

import br.org.editora.model.DAO.GerenteDAO;
import br.org.editora.model.entities.Gerente;
import br.org.editora.model.entities.Usuario;

import java.util.List;

public class GerenteService {
    private AvaliadorDao avaliadorDao = new AvaliadorDao();
    private AutorDao autorDao = new AutorDao();

    public void submeterAvaliador(Avaliador avaliador) {
        
        if (avaliador.getavaliador() == null || avaliador.getavaliador().isEmpty()) {
            throw new IllegalArgumentException("Usuário invalido");

        }
       
        GerenteService.verificarAvaliador(avaliador);
    }

    public void verificarAvaliador(Avaliador avaliador){
        if((avaliadorDao.buscarAvaliadorPorNome(avaliador) == avaliador.getavaliador())){
                throw new IllegalArgumentException("Esse avaliador ja foi cadastrado");
        }
        avaliadorDao.inserir(avaliador);
    }

    //=========================================================================================
       
    public void submeteAutor(Autor autor) {
        
        if (autor.getautor() == null || autor.getautor().isEmpty()) {
            throw new IllegalArgumentException("Usuário invalido");

        }
       
        GerenteService.verificarAutor(autor);
    }

    public void verificarAutor(Autor autor){
        if((autorDao.buscarAvaliadorPorNome(autor) == autor.getautor())){
                throw new IllegalArgumentException("Esse autor ja foi cadastrado");
        }
        autorDao.inserir(autor);
    }

    private GerenteDAO dao = new GerenteDAO();

    // Cadastrar gerente
    public Gerente cadastrarGerente(Usuario usuario) {

        Gerente existente = dao.buscarPorCpf(usuario.getCpf());

        if (existente != null) {
            throw new RuntimeException(
                    "Já existe um gerente  cadastrado com esse CPF."
            );
        }

        Gerente gerente = new Gerente(usuario);

        return dao.inserir(gerente);
    }

    // Buscar gerente por CPF
    public Gerente buscarPorCpf(String cpf) {

        Gerente gerente = dao.buscarPorCpf(cpf);

        if (gerente == null) {
            throw new RuntimeException(
                    "Gerente não encontrado."
            );
        }

        return gerente;
    }

    // Buscar gerente por nome
    public Gerente buscarPorNome(String nome) {

        Gerente gerente = dao.buscarPorNome(nome);

        if (gerente == null) {
            throw new RuntimeException(
                    "Gerente não encontrado."
            );
        }

        return gerente;
    }

    // Listar todos os gerentes
    public List<Gerente> listarGerentes() {
        return dao.listarTodos();
    }

    // Remover gerente
    public void removerGerente(String cpf) {

        Gerente gerente = dao.buscarPorCpf(cpf);

        if (gerente == null) {
            throw new RuntimeException(
                    "Gerente não encontrado."
            );
        }

        dao.excluir(cpf);
    }
}