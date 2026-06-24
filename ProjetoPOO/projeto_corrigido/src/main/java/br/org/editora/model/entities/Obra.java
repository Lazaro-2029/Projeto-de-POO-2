package br.org.editora.model.entities;

import br.org.editora.exceptions.SemAutorException;
import br.org.editora.exceptions.SemGeneroException;
import br.org.editora.exceptions.SemTituloException;
import java.time.LocalDate;

public class Obra {
    private String titulo;
    private String genero;
    private int ano;
    private String status;
    private LocalDate dataAvaliacao;
    private Autor autor;
    private Avaliador avaliadorResponsavel;
    private int idBanco;
    private int id;
    private static int contadorid = 1;

    public Obra(String titulo, String genero, int ano, Autor autor) {
        this.titulo = titulo;
        this.genero = genero;
        this.ano    = ano;
        this.autor  = autor;
        this.status = "PENDENTE";
        this.id = contadorid++;
    }

    public int getIdBanco() { return idBanco; }
    public void setIdBanco(int idBanco) { this.idBanco = idBanco; }

    public int getId() { return id; }

    public Avaliador getAvaliadorResponsavel() { return avaliadorResponsavel; }
    public void setAvaliadorResponsavel(Avaliador a) { this.avaliadorResponsavel = a; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) throws SemTituloException {
        if (titulo == null || titulo.isEmpty()) throw new SemTituloException("Título não pode ser vazio");
        this.titulo = titulo;
    }
    public void alterarTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty()) throw new IllegalArgumentException("Título inválido");
        this.titulo = titulo;
    }

    public String getGenero() { return genero; }
    public void setGenero(String genero) throws SemGeneroException {
        if (genero == null || genero.isEmpty()) throw new SemGeneroException("Gênero não pode ser vazio");
        this.genero = genero;
    }
    public void alterarGenero(String genero) {
        if (genero == null || genero.isEmpty()) throw new IllegalArgumentException("Gênero inválido");
        this.genero = genero;
    }

    public int getAno() { return ano; }
    public void setAno(int ano) {
        if (ano <= 0) throw new IllegalArgumentException("Ano inválido");
        this.ano = ano;
    }
    public void alterarAno(int ano) { setAno(ano); }

    public String getStatus() { return status; }
    public void alterarStatus(String novoStatus) {
        if (!novoStatus.equalsIgnoreCase("APROVADO") && !novoStatus.equalsIgnoreCase("REPROVADO") && !novoStatus.equalsIgnoreCase("PENDENTE"))
            throw new IllegalArgumentException("Status inválido: " + novoStatus);
        this.status = novoStatus.toUpperCase();
    }

    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) throws SemAutorException {
        if (autor == null) throw new SemAutorException("Autor não pode ser nulo");
        this.autor = autor;
    }

    public LocalDate getDataAvaliacao() { return dataAvaliacao; }
    public void setDataAvaliacao(LocalDate d) { this.dataAvaliacao = d; }

    public void exibirDados() {
        System.out.println("Título: " + titulo + " | Gênero: " + genero + " | Ano: " + ano
                + " | Autor: " + (autor != null ? autor.getautor().getNome() : "?")
                + " | Status: " + status);
    }

    @Override
    public String toString() { return titulo + " (" + autor.getautor().getNome() + ")"; }
}
