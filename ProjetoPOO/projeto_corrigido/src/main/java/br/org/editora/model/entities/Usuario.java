package br.org.editora.model.entities;

import br.org.editora.exceptions.SemNomeException;
import br.org.editora.exceptions.SemEnderecoException;
import br.org.editora.exceptions.SemCPFException;

public class Usuario {

    private String nome;
    private String endereco;
    private String cpf;
    private String senhaHash;  // SHA-256 em hex

    public Usuario(String cpf, String nome, String endereco, String senhaHash) {
        this.cpf = cpf;
        this.nome = nome;
        this.endereco = endereco;
        this.senhaHash = senhaHash;
    }

    public String getNome()     { return nome; }
    public String getCpf()      { return cpf; }
    public String getEndereco() { return endereco; }
    public String getSenhaHash(){ return senhaHash; }

    public void setNome(String nome) throws SemNomeException {
        if (nome == null || nome.isBlank())
            throw new SemNomeException("Nome inválido!");
        this.nome = nome;
    }

    public void setCpf(String cpf) throws SemCPFException {
        if (cpf == null || cpf.isBlank())
            throw new SemCPFException("CPF inválido!");
        this.cpf = cpf;
    }

    public void setEndereco(String endereco) throws SemEnderecoException {
        if (endereco == null || endereco.isBlank())
            throw new SemEnderecoException("Endereço inválido!");
        this.endereco = endereco;
    }

    public void setSenhaHash(String senhaHash) {
        if (senhaHash == null || senhaHash.isBlank())
            throw new IllegalArgumentException("Hash de senha inválido!");
        this.senhaHash = senhaHash;
    }

    public void exibirDados() {
        System.out.println("Nome: "     + nome);
        System.out.println("Endereço: " + endereco);
        System.out.println("CPF: "      + cpf);
    }
}
