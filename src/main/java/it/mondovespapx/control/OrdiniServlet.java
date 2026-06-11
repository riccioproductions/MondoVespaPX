package it.mondovespapx.control;

import it.mondovespapx.dao.OrdineDAO;
import it.mondovespapx.model.Ordine;
import it.mondovespapx.model.Utente;
import it.mondovespapx.model.DettaglioOrdine; // Aggiunto l'import
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//Gestisce la visualizzazione dello storico ordini per l'utente loggato
@WebServlet("/area-utente/ordini")
public class OrdiniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
        //Recupera la sessione corrente
        HttpSession session = request.getSession(false);
        Utente utente = (Utente) session.getAttribute("utente");
        OrdineDAO dao = new OrdineDAO(); 
        try {
            //Recupera gli ordini associati all'ID dell'utente
            List<Ordine> ordini = dao.getOrdiniByUtente(utente.getId());           
            if (ordini != null) {
                for (Ordine o : ordini) {
                    List<DettaglioOrdine> dettagli = dao.getDettagliByOrdine(o.getId());
                    o.setDettagli(dettagli);
                }
            }
            //Passa la lista alla JSP
            request.setAttribute("ordini", ordini);          
        } catch (SQLException e) {
            throw new ServletException("Errore durante il recupero degli ordini dal database", e);
        }        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ordini.jsp");
        rd.forward(request, response);
    }
}