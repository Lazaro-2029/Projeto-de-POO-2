package br.org.editora.model.DAO;
import java.net.URL;
import java.sql.*;

public class AutorDAO{
    private final static String URL = "jdbc:mysql://localhost/renan";
    private final static String USER = "renan";
    private final static String PASS = "gadelhalindao"
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