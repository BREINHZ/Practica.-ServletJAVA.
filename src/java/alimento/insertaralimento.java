/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package alimento;

import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author edufi
 */
@WebServlet(name = "insertaralimento", urlPatterns = {"/insertaralimento"})
public class insertaralimento extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Connection conexion;
            Statement sentencia;
            PreparedStatement sentenciaps;
            ResultSet resultado;
            
            // Obtener información del formulario
            //String id_alimento= request.getParameter("id_alimento");
            String nombre = request.getParameter("nombre");
            String kilocalorias = request.getParameter("kilocalorias");
            String fibra = request.getParameter("fibra");
            String carbohidratos = request.getParameter("carbohidratos");
            String grasa= request.getParameter("grasa");
            String proteina= request.getParameter("proteina");
            String cantidad = request.getParameter("cantidad");
            
            // Llamada para cargar el driver que conecta a la base de datos
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            // Obtiener una conexión con la base de datos.
            conexion = DriverManager.getConnection ("jdbc:mysql://localhost:3306/functionaltraining","root", "");

            // Crear un PreparedStatement objeto para enviar instrucciones SQL con parámetros
            sentenciaps = conexion.prepareStatement("INSERT INTO alimento (nombre,kilocalorias,fibra,carbohidratos,grasa,proteina,cantidad) VALUES (?,?,?,?,?,?,?)");
            // Establecer un valor en cada uno de los marcadores de parámetro de la sentencia SQL
            //sentenciaps.setInt(1, Integer.parseInt(id_alimento));
            sentenciaps.setString(1, nombre);
            sentenciaps.setDouble(2, Double.parseDouble(kilocalorias));
            sentenciaps.setDouble(3, Double.parseDouble(fibra));
            sentenciaps.setDouble(4, Double.parseDouble(carbohidratos));
            sentenciaps.setDouble(5, Double.parseDouble(grasa));
            sentenciaps.setDouble(6, Double.parseDouble(proteina));
            sentenciaps.setDouble(7, Double.parseDouble(cantidad));
            sentenciaps.executeUpdate();

            // Crear un Statement objeto para enviar instrucciones SQL sin parámetros
            sentencia = conexion.createStatement();
            // Ejecutar una consulta SQL en la tabla cliente
            resultado = sentencia.executeQuery ("SELECT * FROM alimento");

            // Página HTML con la respuesta
            out.println("<html>");
            out.println("<body>");
            out.println("<h1>Datos Ingresados Exitosamente</h1>");
            out.println("<table align='center' with='75%' border=1>");
            out.println("<tr><th>id_alimento </th><th>nombre</th><th>kilocalorias</th><th>fibra</th><th>carbohidratos</th><th>grasa</th><th>proteina</th><th>cantidad"+"</th></tr>");
            // Visualizar todos los datos de la tabla cliente
            while (resultado.next()){
               out.println("<tr><td>"+resultado.getInt("id_alimento")+"</td><td>"+
	       resultado.getString("nombre")+"</td><td>"+resultado.getDouble("kilocalorias")+"</td><td>" +
               resultado.getDouble("fibra")+"</td><td>"+resultado.getDouble("carbohidratos")+"</td><td>" +
               resultado.getDouble("grasa")+"</td><td>"+resultado.getDouble("proteina")+"</td><td>" +
               resultado.getDouble("cantidad")+"</td></tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
            conexion.close();

        }
        catch (ClassNotFoundException e1) {
                  //Error si no puedo leer el driver 
             System.out.println("ERROR: No encuentro el driver de la BD: "+  e1.getMessage());
        }
        catch (SQLException e2) {
                 //Error SQL: login/passwd mal
             System.out.println("ERROR: Fallo en SQL: "+e2.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
