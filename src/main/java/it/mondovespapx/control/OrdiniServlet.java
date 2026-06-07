package it.mondovespapx.control;

import it.mondovespapx.dao.OrdineDAO;
import it.mondovespapx.model.Ordine;
import it.mondovespapx.model.Utente;
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

//Servlet che gestisce la visualizzazione dello storico ordini per l'utente loggato
@WebServlet("/area-utente/ordini")
public class OrdiniServlet extends HttpServlet {

    //Viene eseguito quando l'utente visita la pagina "I miei ordini"
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recupera la sessione corrente
        HttpSession session = request.getSession(false);
        //Estrae l'oggetto Utente con i dati di chi sta navigando
        Utente utente = (Utente) session.getAttribute("utente");
        // Prepara il DAO per eseguire le query sul database
        OrdineDAO dao = new OrdineDAO();
        try {
            //Recupera dal database la lista di tutti gli ordini associati all'ID dell'utente corrente
            List<Ordine> ordini = dao.getOrdiniByUtente(utente.getId());
            //Passa la lista recuperata alla richiesta per farla leggere alla JSP
            request.setAttribute("ordini", ordini);
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
        //Passa il controllo e i dati al file ordini.jsp per mostrare l'elenco a video
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/ordini.jsp");
        rd.forward(request, response);
    }
}