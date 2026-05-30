package br.org.editora.model.DAO;
import br.org.editora.model.entities.Autor;
import java.net.URL;
import java.sql.*;

public class AutorDAO{
    private final static String URL = "jdbc:mysql://localhost/projetopoo";
    private final static String USER = "root";
    private final static String PASS = "#Projeto21";
    private static Connection con = null;

    public static Connection getConnection(){
        if(con == null){
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e){e.printStackTrace();}
        }
        return con;
    }
    public static void closeConnection(){
        if(con != null){
            try {
                con.close();
            } catch (SQLException e){e.printStackTrace();}
        }
    }
}