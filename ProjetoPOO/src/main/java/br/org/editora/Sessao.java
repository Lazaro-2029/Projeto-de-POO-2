package br.org.editora;

public class Sessao {

    private static Sessao instancia;

    private String cpfUsuario;
    private String nomeUsuario;
    private String tipoPerfil;

    private Sessao() {}

    public static Sessao getInstance() {
        if (instancia == null) {
            instancia = new Sessao();
        }
        return instancia;
    }

    public void iniciar(String cpf, String nome, String tipo) {
        this.cpfUsuario = cpf;
        this.nomeUsuario = nome;
        this.tipoPerfil = tipo.toUpperCase();
    }

    public void encerrar() {
        this.cpfUsuario = null;
        this.nomeUsuario = null;
        this.tipoPerfil = null;
    }

    public String getCpfUsuario()  { return cpfUsuario; }
    public String getNomeUsuario() { return nomeUsuario; }
    public String getTipoPerfil()  { return tipoPerfil; }

    public boolean isGerente()   { return "GERENTE".equals(tipoPerfil); }
    public boolean isAutor()     { return "AUTOR".equals(tipoPerfil); }
    public boolean isAvaliador() { return "AVALIADOR".equals(tipoPerfil); }
    public boolean estaLogado()  { return cpfUsuario != null; }
}
