package it.mondovespapx.control;

import it.mondovespapx.dao.UtenteDAO;
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

//Servlet dedicata alla visualizzazione e modifica dei dati personali dell'utente
@WebServlet("/area-utente/profilo")
public class ProfiloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recupera la sessione attiva senza crearne accidentalmente una nuova
        HttpSession session = request.getSession(false);
        
        //Estrae l'oggetto utente dalla memoria della sessione
        Utente utente = (Utente) session.getAttribute("utente");

        //Passa i dati dell'utente alla JSP per precompilare i campi del form
        request.setAttribute("utente", utente);

        //Mostra la vista del profilo
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/profilo.jsp");
        rd.forward(request, response);
    }

    //Post richiamato quando l'utente invia il form per salvare le modifiche
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Recupera la sessione e i dati attuali dell'utente
        HttpSession session = request.getSession(false);
        Utente utente = (Utente) session.getAttribute("utente");
        //Legge i nuovi valori inseriti nei campi di testo
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String indirizzo = request.getParameter("indirizzo");
        String metodoPagamento = request.getParameter("metodoPagamento");
        //Sovrascrive le variabili dell'oggetto utente con i nuovi valori
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        utente.setIndirizzo(indirizzo);
        utente.setMetodoPagamento(metodoPagamento);

        // Prepara la connessione al database
        UtenteDAO dao = new UtenteDAO();
        try {
            //Esegue l'aggiornamento sul database
            dao.aggiorna(utente);
            
            //Salva nuovamente l'utente in sessione per mantenere i dati aggiornati in memoria
            session.setAttribute("utente", utente);
            
            //Imposta un messaggio di conferma per l'interfaccia
            request.setAttribute("messaggio", "Profilo aggiornato con successo");
            
        } catch (SQLException e) {
            //Intercetta un eventuale errore SQL e blocca la pagina
            throw new ServletException("Errore database", e);
        }

        //Rende nuovamente disponibile l'oggetto utente per ricaricare la pagina con i dati corretti
        request.setAttribute("utente", utente);
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/profilo.jsp");
        rd.forward(request, response);
    }
}