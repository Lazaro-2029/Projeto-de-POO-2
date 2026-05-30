package br.org.editora.model.DAO;
import br.org.editora.exceptions.SemCPFException;
import br.org.editora.model.entities.Usuario;
import java.net.URL;
import java.sql.*;

public class UsuarioDAO{
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
    public Usuario inserir(Usuario entity){
        con = getConnection();
        String sql = "INSERT INTO usuario (cpf,nome,endereco)"
                    + "VALUES(?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,entity.getCpf());
            ps.setString(2,entity.getNome());
            ps.setString(3,entity.getEndereco());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                String cpfGen = rs.getString(1);
                entity.setCpf(String.valueOf(cpfGen));
            }
            ps.close();
        } catch (SQLException e){e.printStackTrace();} catch (SemCPFException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }
}