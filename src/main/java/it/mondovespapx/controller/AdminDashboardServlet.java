package it.mondovespapx.controller;

import it.mondovespapx.dao.OrdineDAO;
import it.mondovespapx.dao.ProdottoDAO;
import it.mondovespapx.model.Ordine;
import it.mondovespapx.model.Prodotto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//Servlet riservata agli amministratori per visualizzare il pannello di controllo principale
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    //Viene eseguito quando l'amministratore accede alla pagina della dashboard
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Crea gli oggetti DAO per poter leggere i dati dal database
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        OrdineDAO ordineDAO = new OrdineDAO();

        try {
            //Estrae dal database la lista completa di tutti i prodotti e tutti gli ordini
            List<Prodotto> prodotti = prodottoDAO.getAllProdotti();
            List<Ordine> ordini = ordineDAO.getAllOrdini();

            //Salva le liste appena recuperate in modo che la pagina web (JSP) possa leggerle e mostrarle
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("ordini", ordini);
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //Passa il controllo al file dashboard.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp");
        rd.forward(request, response);
    }
}