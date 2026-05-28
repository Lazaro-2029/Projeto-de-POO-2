package br.org.editora.model.entities;
class Gerente {
    private Usuario usergerente;

    public Gerente(Usuario gerente){
        setGerente(gerente);
    }

    //=========================================================
    public void setGerente(Usuario gerente){
        if (gerente == null){
            throw new IllegalArgumentException("Gerente inválido");
        }
        this.usergerente = gerente;
    }

    public Usuario getGerente(){
        return usergerente;
    }

    //=========================================================
    public void cadastrarAutor(Autor autor, SystemSearch sistema){
        if (autor == null){
            throw new IllegalArgumentException("Autor inválido");
        }

        sistema.cadastrarAutor(autor);
    }

    public void cadastrarAvaliador(Avaliador avaliador, SystemSearch sistema){
        if (avaliador == null){
            throw new IllegalArgumentException("Avaliador inválido");
        }

        sistema.cadastrarAvaliador(avaliador);
    }

    public void excluirAutor(String nomeAutor, SystemSearch sistema){
        Autor autor = sistema.buscarAutorPorNome(nomeAutor);

        if (autor == null){
            throw new IllegalArgumentException("Autor não encontrado");
        }

        sistema.removerAutor(autor);
    }

    public void excluirAvaliador(String nomeAvaliador, SystemSearch sistema){
        Avaliador avaliador = sistema.buscarAvaliadorPorNome(nomeAvaliador);

        if (avaliador == null){
            throw new IllegalArgumentException("Avaliador não encontrado");
        }

        sistema.removerAvaliador(avaliador);
    }


    //=========================================================
    public void designarAvaliadorParaObra(int idObra, Avaliador avaliador, SystemSearch sistema){
        sistema.designarAvaliador(idObra, avaliador);
    }

    //=========================================================
    public void listarAutores(SystemSearch sistema){
        sistema.listarAutores();
    }

    public void listarAvaliadores(SystemSearch sistema){
        sistema.listarAvaliadores();
    }

    public void listarObras(SystemSearch sistema){
        sistema.listarTodasObras();
    }

    public void listarObrasPendentes(SystemSearch sistema){
        sistema.buscarObraPorStatus("PENDENTE");
    }

    //=========================================================
    public void buscarObraPorTitulo(String titulo, SystemSearch sistema){
        sistema.buscarObraPorTitulo(titulo);
    }

    public void buscarObraPorAutor(String nomeAutor, SystemSearch sistema){
        sistema.buscarObrasPorAutor(nomeAutor);
    }

    //=========================================================
    public void exibirGerente(){
        usergerente.exibirDados();
    }
}