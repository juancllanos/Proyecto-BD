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
        try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Proyecto", "postgres", "postgres")) {
 
            
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
            
            /*ResultSet resultSet = statement.executeQuery("select max(id) from playlist;");
            System.out.println("Consulta exitosa");
            resultSet.next();
            statement.executeUpdate("insert into tiene values ('jcllanos',"+ resultSet.getString("max") +");");
            System.out.println("Insercion exitosa");
            ResultSet resultSet2 = statement.executeQuery("select * from tiene where usuario = 'jcllanos';");
            System.out.printf("%-30.30s %-30.30s%n", "Usuario","ID Playlist");
            while (resultSet2.next()) {
                System.out.printf("%-30.30s %-30.30s%n", resultSet2.getString("usuario"),resultSet2.getString("id_playlist"));
            }*/
            
           // ResultSet resultSet3  = statement.executeQuery("select nombre,autor,genero from canciones where nombre = 'Vida';");
            //System.out.printf("%-30.30s %-30.30s %-30.30s%n", "Nombre","Autor","Genero");
            //while (resultSet3.next()) {
             //   System.out.printf("%-30.30s %-30.30s %-30.30s%n", resultSet3.getString("nombre"),resultSet3.getString("autor"),resultSet3.getString("genero"));
           // }
            
            ResultSet resultSet2 = statement.executeQuery("select * from canciones where nombre = 'Humble';");
            resultSet2.next();
            String b = resultSet2.getString("id");
            System.out.println(b);
            
            ResultSet resultSet3 = statement.executeQuery("select * from playlist where nombre = 'Prueba';");
            resultSet3.next();
            int a = Integer.parseInt(resultSet3.getString("id"));
            resultSet3.close();
            
            System.out.println("Consultas hechas");

            statement.executeUpdate("insert into contiene values (" + a +",'"+ b + "');");
            System.out.println("Insercion exitosa");
                   
        } catch (SQLException e) {
            System.out.println("Conexion fallida");
            e.printStackTrace();
        }
    }

}



 
