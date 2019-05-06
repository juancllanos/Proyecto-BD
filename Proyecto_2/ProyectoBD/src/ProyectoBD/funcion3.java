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
public class funcion3 extends HttpServlet{

	public void doGet(HttpServletRequest request,
	HttpServletResponse response)
	throws ServletException, IOException
											{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<HEAD><TITLE>App Musica</TITLE></HEAD>");
		out.println("<BODY>");
		
		String nombre_c = request.getParameter("nombre_c");
		String nombre_p = request.getParameter("nombre_p");
		String nickname = request.getParameter("nickname");
		String password = request.getParameter("password");
		
		
    	//Intenta establecer conexi�n
    	System.out.println("Estableciendo conexion...");
        try (Connection conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/proyecto", "postgres", "postgres")) {
 
            
            System.out.println("Conexion con la base de datos establecida (PostgreSQL)");
            Statement statement = conexion.createStatement();

            //Revisar existencia de usuario
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
          //Ver si la cancion y la playlist existen:
                System.out.println("Verificando existencia de cancion y de playlist");
                System.out.println();
                ResultSet resultSet2 = statement.executeQuery("select * from canciones where nombre = '"+nombre_c +"';");
                ResultSet resultSet3 = statement.executeQuery("select * from playlist where nombre = '"+nombre_p +"';");
                ResultSet resultSet4 = statement.executeQuery("select * from cancionesYplaylist_usuario where usuario = '"+nickname +"' and "
                		+ " playlist = '" + nombre_p + "';");
                
                if(resultSet2.next() == true && resultSet3.next() == true && resultSet4.next() == true) {
                	
                	System.out.println("Confirmacion, cancion y playlist existen y el usuario posee dicho playlist");
                	System.out.println("Realizando la adicion de la cancion al playlist");
                	statement.executeUpdate("insert into contiene values (" + Integer.parseInt(resultSet3.getString("id"))+",'"+ resultSet2.getString("id") +"');");
                	System.out.println("Cancion añadida a playlist");
                  	
                	out.println("<h1>Cancion añadida a playlist</h1>");
            		out.println("<img src='https://banner2.kisspng.com/20180420/grw/kisspng-check-mark-clip-art-check-vector-5ada906a52c2c6.335712521524273258339.jpg'\n" + 
            				"     width='300'\n" + 
            				"     height='300'></br>");
            		out.println("<body <body background='https://wallpapercave.com/wp/lwfQm84.jpg'>");
            		out.println("</BODY>");
            		out.close();
                	
                	
                	
                	
                }else {
                	out.println("<h1> Usted no posee dicho playlist o La cancion o la playlist no existen.</h1>");
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