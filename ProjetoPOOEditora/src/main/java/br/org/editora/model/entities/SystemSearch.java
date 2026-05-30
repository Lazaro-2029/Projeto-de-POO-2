package br.org.editora.model.entities;
import java.util.ArrayList;

public class SystemSearch {
    private ArrayList<Obra> obrasGuardadas;
    private ArrayList<Autor>autoresGuardados;
    private ArrayList<Avaliador> avaliadoresGuardados;


    public SystemSearch(){
        this.obrasGuardadas = new ArrayList<>();
        this.autoresGuardados = new ArrayList<>();
        this.avaliadoresGuardados = new ArrayList<>();
    }

    public void cadastrarAutor(Autor novoautor){
        if (novoautor == null){
            throw new IllegalArgumentException("cadastro de autor inválido no sistema");
        }
        autoresGuardados.add(novoautor);
    }

    public void cadastrarAvaliador(Avaliador novoavaliador){
        if (novoavaliador == null){
            throw new IllegalArgumentException("cadastro de avaliador inválido no sistema");
        }
        avaliadoresGuardados.add(novoavaliador);
    }

    public void cadastrarObra(Obra novaobra){
        if (novaobra == null){
            throw new IllegalArgumentException("cadastro de obra inválido no sistema");
        }
        obrasGuardadas.add(novaobra);
    }

    public void removerObra(Obra obra){
        obrasGuardadas.remove(obra);
    }

    public void removerAutor(Autor autor){
        if (autor == null){
            throw new IllegalArgumentException("Autor inválido para remoção");
        }

        for (Obra obra : autor.getObras()){
            obrasGuardadas.remove(obra);
        }

        autoresGuardados.remove(autor);
    }

    public void removerAvaliador(Avaliador avaliador){
        if (avaliador == null){
            throw new IllegalArgumentException("Avaliador inválido para remoção");
        }

        for (Obra obra : obrasGuardadas){
            if (obra.getAvaliadorResponsavel() == avaliador){
                obra.setAvaliadorResponsavel(null);
            }
        }

        avaliadoresGuardados.remove(avaliador);
    }
    //---------------------------------------------------------------
    public Obra buscarporObra(int id){
        for (Obra Obra : obrasGuardadas){
            if (Obra.getId() == id){
                return Obra;
            }
        }
        return null;
    }
    public Autor buscarAutorPorNome(String nome) {
        for (Autor autor : autoresGuardados) {
            if (autor.getautor().getNome().equalsIgnoreCase(nome)) {
                return autor;
            }
        }
        return null;
    }

    public Avaliador buscarAvaliadorPorNome(String nome) {
        for (Avaliador avaliador : avaliadoresGuardados) {
            if (avaliador.getavaliador().getNome().equalsIgnoreCase(nome)) {
                return avaliador;
            }
        }
        return null;
    }

    public void buscarObraPorTitulo(String titulo){
        boolean encontrou = false;

        for (Obra obra : obrasGuardadas){
            if (obra.getTitulo().equalsIgnoreCase(titulo)){
                obra.exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
        }
        if (!encontrou){
            System.out.println("Nenhuma obra encontrada com esse título.");
        }
    }
    public void buscarObraPorAno(int ano){
        boolean encontrou = false;

        for (Obra obra : obrasGuardadas){
            if (obra.getAno() == ano){
                obra.exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
        }

        if (!encontrou){
            System.out.println("Nenhuma obra encontrada nesse ano.");
        }
    }

    public void buscarObraPorStatus(String status){
        boolean encontrou = false;

        for (Obra obra : obrasGuardadas){
            if (obra.getStatus().equalsIgnoreCase(status)){
                obra.exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
        }

        if (!encontrou){
            System.out.println("Nenhuma obra encontrada com esse status.");
        }
    }

    public void buscarObrasPorAutor(String nomeAutor){
        boolean encontrou = false;

        for (Obra obra : obrasGuardadas){
            if (obra.getAutor().getautor().getNome().equalsIgnoreCase(nomeAutor)){
                obra.exibirDados();
                System.out.println("-------------");
                encontrou = true;
            }
        }

        if (!encontrou){
            System.out.println("Nenhuma obra encontrada para esse autor.");
        }
    }
    //------------------------------------------------------------
    public void listarTodasObras() {
        if (obrasGuardadas.isEmpty()) {
            System.out.println("Nenhuma obra listada no sistema.");
            return;
        }

        for (Obra obra : obrasGuardadas) {
            obra.exibirDados();
            System.out.println("-------------");
        }
    }

    public void listarAutores() {
        if (autoresGuardados.isEmpty()) {
            System.out.println("Nenhum autor listado no sistema.");
            return;
        }

        for (Autor autor : autoresGuardados) {
            autor.getautor().exibirDados();
            System.out.println("-------------");
        }
    }

    public void listarAvaliadores() {
        if (avaliadoresGuardados.isEmpty()) {
            System.out.println("Nenhum avaliador listado no sistema.");
            return;
        }

        for (Avaliador avaliador : avaliadoresGuardados) {
            avaliador.getavaliador();
            System.out.println("-------------");
        }
    }

    public void designarAvaliador(int idObra, Avaliador avaliador){
        Obra obra = buscarporObra(idObra);

        if (obra == null){
            throw new IllegalArgumentException("Obra não encontrada");
        }

        if (avaliador == null){
            throw new IllegalArgumentException("Avaliador inválido");
        }

        obra.setAvaliadorResponsavel(avaliador);
    }
    public ArrayList<Autor> getAutores() {
        return autoresGuardados;
    }

    public ArrayList<Avaliador> getAvaliadores() {
        return avaliadoresGuardados;
    }

    public ArrayList<Obra> getObras() {
        return obrasGuardadas;
    }
}
