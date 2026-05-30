package org.example;

import br.org.editora.exceptions.SemCPFException;
import br.org.editora.exceptions.SemEnderecoException;
import br.org.editora.exceptions.SemNomeException;
import br.org.editora.model.DAO.UsuarioDAO;
import br.org.editora.model.entities.Usuario;

public class Main{
    public static void main(String[] args) throws SemCPFException, SemNomeException, SemEnderecoException {
        UsuarioDAO d = new UsuarioDAO();
        Usuario a = new Usuario("","","");
        try {
            a.setCpf("12345678");
        } catch (SemCPFException e) {
            throw new RuntimeException(e);
        }
        try {
            a.setNome("Roberto");
        } catch (SemNomeException e) {
            throw new RuntimeException(e);
        }
        try {
            a.setEndereco("Tibau, rua 1");
        } catch (SemEnderecoException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Inserido com sucesso: "+ d.inserir(a).getCpf());
        System.out.println(UsuarioDAO.getConnection());
    }
}