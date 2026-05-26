package it.mondovespapx.controller;

import it.mondovespapx.model.ElementoCarrello;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Servlet che gestisce le operazioni del carrello 
@WebServlet("/carrello")
public class CarrelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recupera la sessione dell'utente
        HttpSession session = request.getSession();
        //Cerca il carrello salvato nella sessione
        @SuppressWarnings("unchecked")
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) session.getAttribute("carrello");
        // Se l'utente non ha ancora un carrello, ne crea uno vuoto e lo salva nella sessione
        if (carrello == null) {
            carrello = new ArrayList<>();
            session.setAttribute("carrello", carrello);
        }
        //Rende il carrello disponibile alla pagina web
        request.setAttribute("carrello", carrello);

        //Calcola il prezzo totale sommando i totali di ogni singola riga
        double totale = 0;
        for (ElementoCarrello e : carrello) {
            totale += e.getTotale();
        }
        
        //Passa il totale alla pagina web
        request.setAttribute("totale", totale);

        //Invia i dati e mostra la pagina carrello.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/carrello.jsp");
        rd.forward(request, response);
    }

    //Gestisce le azioni di modifica del carrello inviate tramite i form aggiungi e rimuovi
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Legge l'azione che l'utente vuole eseguire
        String azione = request.getParameter("azione");
        
        //Recupera la sessione corrente
        HttpSession session = request.getSession();
        //Recupera il carrello dalla sessione o lo crea vuoto se non esiste
        @SuppressWarnings("unchecked")
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new ArrayList<>();
            session.setAttribute("carrello", carrello);
        }
        // Se l'azione è "aggiungi" oppure non è specificata
        if ("aggiungi".equals(azione) || azione == null) {
            
            //Legge i dati del prodotto inviati dal form e li converte nei formati corretti
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            String nomeProdotto = request.getParameter("nomeProdotto");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            boolean trovato = false;
            
            //Cerca se il prodotto è già presente nel carrello
            for (ElementoCarrello e : carrello) {
                if (e.getIdProdotto() == idProdotto) {
                    //Se esiste già, aggiorna solo la quantità sommandola a quella vecchia
                    e.setQuantita(e.getQuantita() + quantita);
                    trovato = true;
                    break;
                }
            }

            //Se il prodotto non era già nel carrello crea una nuova riga e lo aggiunge
            if (!trovato) {
                carrello.add(new ElementoCarrello(idProdotto, nomeProdotto, prezzo, quantita));
            }

        //Se l'azione è rimuovi legge l'ID del prodotto da eliminare e lo rimuove
        } else if ("rimuovi".equals(azione)) {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            carrello.removeIf(e -> e.getIdProdotto() == idProdotto);
        }

        //Ricarica la pagina del carrello per evitare il reinvio del form e acquisti doppi
        response.sendRedirect(request.getContextPath() + "/carrello");
    }
}