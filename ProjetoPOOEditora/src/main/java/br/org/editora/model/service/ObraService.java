package br.org.editora.model.service;
import br.org.editora.model.entities.Obra;

public class ObraService {
    private ObraDao obraDao = new ObraDao();

    public void submeter(Obra obra) {
        
        if (obra.getTitulo() == null || obra.getTitulo().isEmpty()) {
            throw new SemTituloException("Título invalido!");
        }
        if (obra.getGenero() == null || obra.getGenero().isEmpty()){
            throw new SemGeneroException("Genero invalido!");
        }
        if (obra.getAno() == null || obra.getAno().isEmpty()){
            throw new SemAnoException("Ano de publicação invalido!"); 
        }
        if (obra.getAutor() == null) {
            throw new SemAutorException("Autor invalido!");
        }
        obraDao.verificar(obra);
    
    }

    public void verificar(Obra obra){
        if((obraDao.buscarObraPorTitulo(obra) == obra.getTitulo())&&(obraDao.buscarObraPorAutor(obra) == obra.getAutor())){
                throw new IllegalArgumentException("Essa obra já existe!");
        }
        obraDAO.inserir(obra);
    }
}                                                                                                                                   
