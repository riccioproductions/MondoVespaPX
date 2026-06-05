package it.mondovespapx.controller;

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

//Servlet riservata agli amministratori per visualizzare e gestire lo stato di tutti gli ordini
@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {

    //Recupera l'elenco completo degli ordini e carica l'interfaccia di amministrazione
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Inizializza il DAO per l'accesso ai dati degli ordini
        OrdineDAO dao = new OrdineDAO();
        try {
            List<Ordine> ordini = dao.getAllOrdini();
            //Inserisce la lista nella request per passarla poi alla pagina JSP
            request.setAttribute("ordini", ordini);
            
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //Indirizza la richiesta alla vista (JSP) dedicata all'elenco ordini per l'admin
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/ordini.jsp");
        rd.forward(request, response);
    }

    //Riceve i dati dal form di amministrazione per modificare lo stato di uno specifico ordine
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Estrae i parametri inviati
        int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
        String stato = request.getParameter("stato");
        OrdineDAO dao = new OrdineDAO();

        try {
            //Esegue l'aggiornamento sul database
            dao.aggiornaStato(idOrdine, stato);
            
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //Fa una redirect così impedisce all'amministratore di inviare più volte lo stesso comando.
        response.sendRedirect(request.getContextPath() + "/admin/ordini");
    }
}