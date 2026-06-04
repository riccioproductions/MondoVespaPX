package it.mondovespapx.controller;

import it.mondovespapx.dao.OrdineDAO;
import it.mondovespapx.model.DettaglioOrdine;
import it.mondovespapx.model.ElementoCarrello;
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
import java.util.ArrayList;
import java.util.List;

//Servlet che gestisce la procedura di riepilogo e conferma dell'acquisto
@WebServlet("/area-utente/ordine")
public class OrdineServlet extends HttpServlet {

    //Mostra la pagina di riepilogo prima della conferma definitiva
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recupera la sessione
        HttpSession session = request.getSession(false);
        //Estrae il carrello dalla sessione
        @SuppressWarnings("unchecked")
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) session.getAttribute("carrello");
        //Controllo di sicurezza: se il carrello è vuoto o non esiste, 
        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }
        double totale = 0;
        for (ElementoCarrello e : carrello) {
            totale += e.getTotale();
        }
        //Passa carrello e totale alla pagina JSP per visualizzarli
        request.setAttribute("carrello", carrello);
        request.setAttribute("totale", totale);
        //Mostra la pagina di riepilogo dell'ordine
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/riepilogo.jsp");
        rd.forward(request, response);
    }

    //Viene chiamato quando l'utente clicca su "Conferma Ordine"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recupera sessione, utente loggato e contenuto del carrello
        HttpSession session = request.getSession(false);
        Utente utente = (Utente) session.getAttribute("utente");
        @SuppressWarnings("unchecked")
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) session.getAttribute("carrello");
        //Doppio controllo di sicurezza: evita acquisti multipli se l'utente ricarica la pagina
        if (carrello == null || carrello.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/carrello");
            return;
        }

        //Calcola l'importo totale da salvare nel database
        double totale = 0;
        for (ElementoCarrello e : carrello) {
            totale += e.getTotale();
        }

        //Crea l'oggetto di testa dell'ordine assegnandolo all'utente corrente
        Ordine ordine = new Ordine();
        ordine.setIdUtente(utente.getId());
        ordine.setTotale(totale);
        //repara la lista che conterrà i singoli prodotti acquistati
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        //Converte ogni riga del carrello temporaneo in una riga definitiva dell'ordine
        for (ElementoCarrello e : carrello) {
            DettaglioOrdine d = new DettaglioOrdine();
            d.setIdProdotto(e.getIdProdotto());
            d.setQuantita(e.getQuantita());
            d.setPrezzoUnitario(e.getPrezzo());
            //Aggiunge il dettaglio alla lista pronta per il salvataggio
            dettagli.add(d);
        }

        OrdineDAO dao = new OrdineDAO();

        try {
            //Esegue la transazione sul database: salva sia l'intestazione che i dettagli in blocco
            dao.inserisciOrdine(ordine, dettagli);
            
            //Operazione completata: svuota il carrello dalla memoria per evitare acquisti doppi
            session.removeAttribute("carrello");
            
            //Reindirizza l'utente alla pagina che mostra lo storico dei suoi ordini
            response.sendRedirect(request.getContextPath() + "/area-utente/ordini");
            
        } catch (SQLException e) {
              throw new ServletException("Errore database", e);
        }
    }
}