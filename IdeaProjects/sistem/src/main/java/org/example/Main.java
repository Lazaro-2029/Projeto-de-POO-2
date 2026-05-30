package org.example;
import br.org.editora.DAO.AutorDAO;

public class Main{
    public static void main(String[] args){
        System.out.println(AutorDAO.getConnection());
    }
}