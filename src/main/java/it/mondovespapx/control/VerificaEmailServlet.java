package it.mondovespapx.control;

import it.mondovespapx.dao.UtenteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

//Servlet verifica tramite AJAX la disponibilità di un'email
@WebServlet("/verifica-email")
public class VerificaEmailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Estrae il parametro email dalla query string dell'URL
        String email = request.getParameter("email");
        //Imposta l'intestazione HTTP per la response per contenuto JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        //Se la mail non esiste
        if (email == null || email.trim().isEmpty()) {
            out.print("{\"esiste\": false}");
            return;
        }
        UtenteDAO dao = new UtenteDAO();
        try {
            boolean esiste = dao.emailEsiste(email.trim());
            out.print("{\"esiste\": " + esiste + "}");
            
        } catch (SQLException e) {
            response.setStatus(500);
            out.print("{\"esiste\": false}");
        }
    }
}