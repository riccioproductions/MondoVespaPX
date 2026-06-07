package it.mondovespapx.control;

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

        //Legge il parametro azione per capire quale operazione eseguire
        String azione = request.getParameter("azione");
        HttpSession session = request.getSession();
        //Recupera il carrello dalla memoria della sessione
        @SuppressWarnings("unchecked")
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) session.getAttribute("carrello");
        //Se il carrello non esiste, inizializza una nuova lista vuota e la salva in sessione
        if (carrello == null) {
            carrello = new ArrayList<>();
            session.setAttribute("carrello", carrello);
        }
        //aggiungere un nuovo prodotto
        if ("aggiungi".equals(azione) || azione == null) {           
            //Estrae i dettagli del prodotto
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            String nomeProdotto = request.getParameter("nomeProdotto");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            boolean trovato = false;
            
            //Scorre il carrello per verificare se il prodotto è già presente
            for (ElementoCarrello e : carrello) {
                if (e.getIdProdotto() == idProdotto) {
                    //Se esiste, somma la nuova quantità a quella già presente
                    e.setQuantita(e.getQuantita() + quantita);
                    trovato = true;
                    break; 
                }
            }

            //Se il prodotto non era nel carrello, crea un nuovo elemento e lo accoda alla lista
            if (!trovato) {
                carrello.add(new ElementoCarrello(idProdotto, nomeProdotto, prezzo, quantita));
            }

        //Rimuovere completamente un prodotto
        } else if ("rimuovi".equals(azione)) {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            //rimuove l'elemento che corrisponde all'ID
            carrello.removeIf(e -> e.getIdProdotto() == idProdotto);

        //Modificare la quantità esatta di un prodotto già presente
        } else if ("aggiorna".equals(azione)) {
            int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            //Scorre il carrello per trovare il prodotto da aggiornare
            for (ElementoCarrello e : carrello) {
                if (e.getIdProdotto() == idProdotto) {
                    //Se l'utente imposta la quantità a 0 o meno, il prodotto viene rimosso
                    if (quantita <= 0) {
                        carrello.remove(e);
                    } else {
                        //Altrimenti sovrascrive la vecchia quantità con il nuovo valore esatto
                        e.setQuantita(quantita);
                    }
                    break; 
                }
            }

        //Svuotare l'intero carrello
        } else if ("svuota".equals(azione)) {
            //Elimina istantaneamente tutti gli elementi presenti nella lista
            carrello.clear();
        }

        //Ricarica la pagina per evitare che l'utente invii la stessa operazione due volte
        response.sendRedirect(request.getContextPath() + "/carrello");
    }
}