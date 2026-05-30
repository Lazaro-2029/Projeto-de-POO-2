package br.org.editora.model.entities;
import java.util.ArrayList;
import java.time.LocalDate;

public class Avaliador {
    private Usuario usuarioAvaliador;
    private ArrayList<Obra> obrasAvaliadas;

    public Avaliador(Usuario avaliador){
        setavaliador(avaliador);
        this.obrasAvaliadas = new ArrayList<>();

    }

    //-----------------------------------------------------

    public void setavaliador( Usuario useravaliador){
        if (useravaliador == null){
            throw new IllegalArgumentException("avaliador invalido bebe");
        }
        this.usuarioAvaliador = useravaliador;
    }

    public Usuario getavaliador(){
        return usuarioAvaliador;
    }

    public ArrayList<Obra> getObrasAvaliadas(){
        return obrasAvaliadas;
    }
    //--------------------------------------------------------------
    public void aprovarObra(int id, SystemSearch sistema){
        Obra obra = sistema.buscarporObra(id);

        if(obra == null){
            throw new IllegalArgumentException("Obra não encontrada");
        }
        if (!obra.getStatus().equalsIgnoreCase("PENDENTE")) {
            throw new IllegalArgumentException("Essa obra já foi avaliada");
        }

        if (obra.getAvaliadorResponsavel() != this){
            throw new IllegalArgumentException("Você não é o avaliador responsável por esta obra");
        }
        obra.alterarStatus("APROVADO");
        obra.setDataAvaliacao(LocalDate.now());
        obrasAvaliadas.add(obra);
    }
    //--------------------------------------------------------
    public void reprovarObra(int id, SystemSearch sistema){
        Obra obra = sistema.buscarporObra(id);

        if(obra == null){
            throw new IllegalArgumentException("Obra não encontrada");
        }

        if (!obra.getStatus().equalsIgnoreCase("PENDENTE")) {
            throw new IllegalArgumentException("Essa obra já foi avaliada");
        }

        if (obra.getAvaliadorResponsavel() != this){
            throw new IllegalArgumentException("Você não é o avaliador responsável por esta obra");
        }

        obra.alterarStatus("REPROVADO");
        obra.setDataAvaliacao(LocalDate.now());
        obrasAvaliadas.add(obra);
    }
//------------------------------------------------------

    public void listarObrasAvaliadas(){
        if(obrasAvaliadas.isEmpty()){
            System.out.println("Nenhuma obra avaliada.");
            return;
        }

        for(Obra obra : obrasAvaliadas){
            obra.exibirDados();
            System.out.println("-----------");
        }
    }

}
