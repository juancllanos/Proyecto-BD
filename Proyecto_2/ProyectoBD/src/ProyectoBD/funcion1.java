// CREAR PLAYLIST

package ProyectoBD;
import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/ProyectoBD/funcion1")
public class funcion1 extends HttpServlet{

	public void doGet(HttpServletRequest request,
	HttpServletResponse response)
	throws ServletException, IOException
											{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<HEAD><TITLE>App Musica</TITLE>");
	    out.println("<font color=\"#FFFFFF\">");
	    out.println("</HEAD>");
		out.println("<BODY>");
		
		String nombre_p = request.getParameter("nombre");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		
		
    	//Intenta establecer conexi�n
    	System.out.println("Estableciendo conexion...");
        try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Proyecto", "postgres", "postgres")) {
 
            
            System.out.println("Conexion con la base de datos establecida (PostgreSQL)");
            Statement statement = conexion.createStatement();
            
          // Revisar existencia de usuario 
            String consulta = " select * from usuario where nickname = '"+nickname+"' and contraseña = '"  + password +  "';";
            System.out.println("Consultando la base de datos...");
            System.out.println(consulta);
            ResultSet resultSet = statement.executeQuery(consulta);
            
            if(resultSet.next() == false ) {
            	
            	out.println("<h1> Error usuario o clave incorrecta</h1>");
        		out.println("<img src='http://4.bp.blogspot.com/-hmRb2YpGRNk/UR0KU1v49yI/AAAAAAAAAcI/fmjlzmlXFs0/s1600/Error.png'\n" + 
        				"     width='300'\n" + 
        				"     height='300'></br>");
        		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
        		out.println("</BODY>");
        		out.close();
        		
            	
            	
            }else {
                
                System.out.println("Imprimiendo resultados...");
                System.out.printf("%-30.30s  %-30.30s%n", "Usuario", "Contraseña");	
                while (resultSet.next()) {
                System.out.printf("%-30.30s %-30.30s%n",resultSet.getString("nickname"), resultSet.getString("contraseña"));
            }              
          //Insercion de playlist
            System.out.println("Consultando la base de datos para insercion...");
            statement.executeUpdate("insert into playlist(nombre) values ('"  + nombre_p   +  "');");
            System.out.println("Insercion realizada");
            
          //Asociacion a usuario
            System.out.println("Asociacion a usuario");
            ResultSet resultSet2 = statement.executeQuery("select max(id) from playlist;");
            System.out.println("Consulta id playlist");
            resultSet2.next();
            statement.executeUpdate("insert into tiene values ('"  + nickname   +  "',"+ resultSet2.getString("max")   + ");" );
            System.out.println("Insercion y asociacion realizada");
            
            
        	out.println("<h1>Nueva playlist creada con exito</h1>");
    		out.println("<img src='https://images.vexels.com/media/users/3/157932/isolated/preview/951a617272553f49e75548e212ed947f-curved-check-mark-icon-by-vexels.png'\n" + 
    				"     width='300'\n" + 
    				"     height='300'></br>");
        	out.println("<body <body background = 'https://wallpapercave.com/wp/lwfQm84.jpg'> ");
    		out.println("</BODY>");
    		out.close();
           
            }
        } catch (SQLException e) {
            System.out.println("Conexion fallida");
            e.printStackTrace();
        }
		}
	}