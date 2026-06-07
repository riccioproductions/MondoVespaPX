package it.mondovespapx.control;
import it.mondovespapx.dao.OrdineDAO;
import it.mondovespapx.model.Ordine;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//Servlet dedicata all'amministrazione degli ordini
@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {

    //Carica la lista degli ordini applicando eventuali filtri
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Legge i parametri di filtro opzionali
        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String cliente = request.getParameter("cliente");
        OrdineDAO dao = new OrdineDAO();

        try {
            List<Ordine> ordini = dao.getOrdiniFiltrati(dataInizio, dataFine, cliente);
            request.setAttribute("ordini", ordini);
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //Reinserisce i parametri di filtro nella request per mantenere visibili all'utente i filtri attualmente attivi.
        request.setAttribute("dataInizio", dataInizio != null ? dataInizio : "");
        request.setAttribute("dataFine", dataFine != null ? dataFine : "");
        request.setAttribute("cliente", cliente != null ? cliente : "");
        
        //Passa la richiesta alla pagina jsp del pannello admin
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/ordini.jsp");
        rd.forward(request, response);
    }

    //Gestisce l'aggiornamento dello stato di spedizione di un singolo ordine
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Estrae l'ID dell'ordine e il nuovo stato dal form inviato in POST
        int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
        String stato = request.getParameter("stato");
        OrdineDAO dao = new OrdineDAO();
        try {
            dao.aggiornaStato(idOrdine, stato);
            
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //reindirizza l'utente alla pagina degli ordini per prevenire doppi invii
        response.sendRedirect(request.getContextPath() + "/admin/ordini");
    }
}