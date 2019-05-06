package ProyectoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class conexion {
    public static void main(String[] args) {
    	
    	//Intenta establecer conexi�n
    	System.out.println("Estableciendo conexi�n...");
        try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/proyecto", "postgres", "postgres")) {
 
            
            System.out.println("Conexion con la base de datos establecida (PostgreSQL)");
            Statement statement = conexion.createStatement();
                        
          //Ejemplo inserci�n
            //System.out.println("Insercion");
            //statement.executeUpdate("insert into usuario values ('juanfperez', 'juanfperez@hotmail.com', 'Juan F. Perez', '312');");
            //System.out.println("Insercion satisfactoria");
            
          //Ejemplo consulta
            System.out.println("Consultando la base de datos...");
           // System.out.println("Eliminacion");
            //statement.executeUpdate("delete from contiene where id_playlist = " + 6 + "and id_cancion = '"+  "030" + "';");
            //System.out.println("Eliminacion exitosa");
            
            ResultSet resultSet= statement.executeQuery("select nombre, autor, canciones.genero from"
            		+ " canciones join ( select genero from cancionesYplaylist_usuario where"
            		+ " usuario = 'jcllanos' and playlist = 'Perreo'  group by genero) as A on"
            		+ " canciones.genero = A.genero;");
            System.out.println("Consulta exitosa");
            System.out.printf("%-30.30s %-30.30s %-30.30s%n", "Cancion","Autor","Genero");
            while (resultSet.next()) {
                System.out.printf("%-30.30s %-30.30s  %-30.30s%n", resultSet.getString("nombre"),resultSet.getString("autor"),resultSet.getString("genero"));
            }
                   
        } catch (SQLException e) {
            System.out.println("Conexi�n fallida");
            e.printStackTrace();
        }
    }

}



 
