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

@WebServlet("/ProyectoBD/funcion5")
public class funcion5 extends HttpServlet{

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
		
		String nombre_p = request.getParameter("nombre_p");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		
		
    	//Intenta establecer conexi�n
    	System.out.println("Estableciendo conexion...");
        try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Proyecto", "postgres", "postgres")) {
 
            
            System.out.println("Conexion con la base de datos establecida (PostgreSQL)");
            Statement statement = conexion.createStatement();
            String consulta = " select * from usuario where nickname = '"+nickname+"' and contraseña = '"  + password +  "';";
            System.out.println("Consultando la base de datos...");
            System.out.println(consulta);
            ResultSet resultSet = statement.executeQuery(consulta);
            
            //Ver si el usuario y contraseña son correctos.
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
          //Verificar que la playlist pertence al usuario:
                System.out.println("Verificando existencia de cancion y de playlist");
                System.out.println();
                ResultSet resultSet3 = statement.executeQuery("select * from cancionesYplaylist_usuario where usuario = '"+nickname +"' and "
                		+ " playlist = '" + nombre_p + "';");
                boolean a3 = resultSet3.next();
                
                if(a3) {
                	
                	System.out.println("Confirmacion, el usuario posee dicho playlist");
                    ResultSet resultSet4= statement.executeQuery("select *  from (select nombre, autor, canciones.genero " 
                    		+ "	  from canciones join ( select genero from cancionesYplaylist_usuario " 
                    		+ "	  where usuario = '" + nickname +"' and playlist = '"+ nombre_p +"' group by genero) as A" + 
                    		"	  on canciones.genero = A.genero) as B except(select cancion,autor,genero from cancionesYplaylist_usuario \n" + 
                    		"	  where usuario = '" +nickname +"' and playlist = '"+ nombre_p+ "'); ");
                    
                	out.println("<table bgcolor = '#FFFFFF' BORDER COLS=3>");
                		out.println(" <tr> <td>Nombre</td><td>Autor</td>" +
                				" <td>Genero</td> </tr>");
                		while(resultSet4.next()) {
                			out.println("<tr> <td>" + resultSet4.getString("nombre") + "</td>" +
                					"<td>" + resultSet4.getString("autor") + "</td>" +
                					"<td>" + resultSet4.getString("genero") + "</td></tr>");
                		}
                		out.println("</table>");
                		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
                		out.println("</BODY>");
                		out.close(); 
  
                	
                }else {
                	out.println("<h1> Usted no posee dicho playlist o la playlist esta vacia.</h1>");
            		out.println("<img src='http://4.bp.blogspot.com/-hmRb2YpGRNk/UR0KU1v49yI/AAAAAAAAAcI/fmjlzmlXFs0/s1600/Error.png'\n" + 
            				"     width='300'\n" + 
            				"     height='300'></br>");
            		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
            		out.println("</BODY>");
            		out.close();              	
                }
            
            
            }
        } catch (SQLException e) {
            System.out.println("Conexion fallida");
            e.printStackTrace();
        }
		}
	}