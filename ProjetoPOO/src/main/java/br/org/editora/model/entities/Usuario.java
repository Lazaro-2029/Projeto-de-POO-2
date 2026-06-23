package br.org.editora.model.entities;
import br.org.editora.exceptions.SemNomeException;
import br.org.editora.exceptions.SemEnderecoException;
import br.org.editora.exceptions.SemCPFException;

public class Usuario {

    private String nome;
    private String endereco;
    private String cpf;

    public Usuario (String cpf, String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws SemNomeException{
        if (nome == null || nome.isEmpty()) {
            throw new SemNomeException("Nome inválido!");
        }
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setCpf(String cpf) throws SemCPFException {
        if (cpf == null || cpf.isEmpty()) {
            throw new SemCPFException("CPF inválido!");
        }
        this.cpf = cpf;
    }

    public void setEndereco(String endereco) throws SemEnderecoException {
        if( endereco == null || endereco.isEmpty()){
            throw new SemEnderecoException("Endereço inválido!");
        }
        this.endereco = endereco;
    }

    public void alterarnome(String nome){
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public void alterarendereco(String endereco){
        if (endereco == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.endereco = endereco;
    }

    public void exibirDados() {
        System.out.println("Nome: " + nome);
        System.out.println("Endereço: " + endereco);
        System.out.println("CPF: " + cpf);
    }
}
