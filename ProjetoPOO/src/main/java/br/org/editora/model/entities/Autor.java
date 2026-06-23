package br.org.editora.model.entities;
import br.org.editora.exceptions.SemGeneroException;
import br.org.editora.exceptions.SemTituloException;

import java.util.ArrayList;


public class Autor {
    private Usuario user;
    private ArrayList<Obra> obras;

    public Autor(Usuario user){
        setUser(user);
        this.obras = new ArrayList<>();
    }

    public void setUser(Usuario user) {
        if (user == null) {
            throw new IllegalArgumentException("USUÁRIO VAZIO NÃO ACEITO");
        }
        this.user = user;
    }
    public Usuario getautor() {
        return user;
    }

    public void cadastrarObra(String nome, String genero, int ano, SystemSearch sistema){
        Obra novaobra = new Obra(nome, genero, ano,this);
        obras.add(novaobra);
        sistema.cadastrarObra(novaobra);
    }

    public void listarObras(){
        if (obras.isEmpty()){
            System.out.println("Nenhuma obra encontrada.");
            return;
        }

        for (Obra obra : obras){
            obra.exibirDados();
            System.out.println("----------");
        }
    }
    public void excluirObra(int id, SystemSearch sistema){
        Obra obra = buscarObraPorId(id);

        if (obra == null){
            throw new IllegalArgumentException("Obra não encontrada");
        }

        if (!obra.getStatus().equalsIgnoreCase("PENDENTE")){
            throw new IllegalArgumentException("Só é possível excluir obras pendentes");
        }
        obras.remove(obra);
        sistema.removerObra(obra);
    }

    public Obra buscarObraPorId(int id) {
        for (Obra obra : obras) {
            if (obra.getId() == id) {
                return obra;
            }
        }
        return null;
    }

    public void alterarTituloObra(int id, String novoTitulo) throws SemTituloException {
        Obra obra = buscarObraPorId(id);

        if (obra == null) {
            throw new IllegalArgumentException("Obra não encontrada");
        }
        if (!obra.getStatus().equalsIgnoreCase("PENDENTE")) {
            throw new IllegalArgumentException("Só é possível alterar ou excluir obras pendentes");
        }
        obra.setTitulo(novoTitulo);
    }

    public void alterarGeneroObra(int id, String novoGenero) throws SemGeneroException {
        Obra obra = buscarObraPorId(id);

        if (obra == null) {
            throw new IllegalArgumentException("Obra não encontrada");
        }
        if (!obra.getStatus().equalsIgnoreCase("PENDENTE")) {
            throw new IllegalArgumentException("Só é possível alterar ou excluir obras pendentes");
        }
        obra.setGenero(novoGenero);
    }

    public void alterarAnoObra(int id, int novoano) {
        Obra obra = buscarObraPorId(id);

        if (obra == null) {
            throw new IllegalArgumentException("Obra não encontrada");
        }
        if (!obra.getStatus().equalsIgnoreCase("PENDENTE")) {
            throw new IllegalArgumentException("Só é possível alterar ou excluir obras pendentes");
        }
        obra.setAno(novoano);
    }

    public ArrayList<Obra> getObras() {
        return obras;
    }

    public void exibirAutor() {
        user.exibirDados();
        System.out.println("Quantidade de obras: " + obras.size());
    }
}