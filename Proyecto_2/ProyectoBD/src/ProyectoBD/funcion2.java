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

@WebServlet("/ProyectoBD/funcion2")
public class funcion2 extends HttpServlet{

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
		
		String tipo = request.getParameter("tipo");
		String nombre = request.getParameter("nombre");
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
            	System.out.println("Usuario o clave incorrecta");
            	out.println("<h1> Error usuario o clave incorrecta</h1>");
        		out.println("<img src='http://4.bp.blogspot.com/-hmRb2YpGRNk/UR0KU1v49yI/AAAAAAAAAcI/fmjlzmlXFs0/s1600/Error.png'\n" + 
        				"     width='300'\n" + 
        				"     height='300'></br>");
        		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
        		out.println("</BODY>");
        		out.close();
        		
            	
            	
            }else {
            	 System.out.println("Usuario iniciado con exito");
            	 System.out.println("Tipo = " + tipo);
            	 
            
          //Busqueda
                if(tipo.equals("cancion")) {
                    System.out.println("Buscando cancion");
                    ResultSet resultSet2 = statement.executeQuery("select nombre,autor,genero from canciones where nombre = '" +nombre+ "';");
                    System.out.println("Consulta cancion");
                    if(!resultSet2.next()) {
                    	
                    	System.out.println("Cancion no encontrada");
                    	out.println("<h1>Lo sentimos la cancion no ha sido encontrada</h1>");
                		out.println("<img src='https://pngimage.net/wp-content/uploads/2018/06/oops-png.png'\n" + 
                				"     width='300'\n" + 
                				"     height='300'></br>");
                		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
                		out.println("</BODY>");
                		out.close();
                    	
                    }else {
                    	System.out.println("Cancion encontrada");               	
                    	out.println("<table bgcolor = '#FFFFFF' BORDER COLS=3>");
                		out.println(" <tr> <td>Nombre</td> <td>Autor</td>" +
                				" <td>Genero</td> </tr>");
                		
                		do{
                			System.out.println("Escribiendo ...");
                			out.println("<tr> <td>" + resultSet2.getString("nombre") + "</td>" +
                					"<td>" + resultSet2.getString("autor") + "</td>" +
                					"<td>" + resultSet2.getString("genero") + "</td></tr>");
                		}while(resultSet2.next());
                		
                		out.println("</table>");
                		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
                		out.println("</BODY>");
                		out.close();                	
                    }
                }
                    if(tipo.equals("playlist")) {
                        System.out.println("Buscando Playlist");
                        ResultSet resultSet3 = statement.executeQuery("select * from cancionesYplaylist_usuario where playlist = '" +nombre+ "';");
                        System.out.println("Consultando Playlist");
                        if(resultSet3.next() == false) {
                        	
                        	System.out.println("Playlist no encontrada");
                        	out.println("<h1>Lo sentimos la playlist no ha sido encontrada o esta vacia</h1>");
                    		out.println("<img src='https://pngimage.net/wp-content/uploads/2018/06/oops-png.png'\n" + 
                    				"     width='300'\n" + 
                    				"     height='300'></br>");
                    		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
                    		out.println("</BODY>");
                    		out.close();
                        	
                        }else {
                        	
                        	System.out.println("Playlist encontrada");
                        	out.println("<table bgcolor = " + "#FFFFFF" + " BORDER COLS=3>");
                    		out.println(" <tr> <td>Nombre</td> <td>Usuario</td> <td>Cancion</td><td>Autor</td>" +
                    				" <td>Genero</td> </tr>");
                    		
                    		
                    		do {
                    			System.out.println("Escribiendo ...");
                    			out.println("<tr> <td>" + resultSet3.getString("playlist") + "</td>" +
                    					"<td>" + resultSet3.getString("usuario") + "</td>" +
                    					"<td>" + resultSet3.getString("cancion") + "</td>" +
                    					"<td>" + resultSet3.getString("autor") + "</td>" +
                    					"<td>" + resultSet3.getString("genero") + "</td></tr>");
                    		} while(resultSet3.next());
                    		out.println("</table>");
                    		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
                    		out.println("</BODY>");
                    		out.close();                	
                        } 
                    }
            }
            
        } catch (SQLException e) {
            System.out.println("Conexion fallida");
            e.printStackTrace();
        }
		}
	}