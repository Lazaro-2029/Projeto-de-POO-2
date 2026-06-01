package br.org.editora.model.service;
import br.org.editora.model.entities.Gerente;

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

}
