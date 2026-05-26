package it.mondovespapx.controller;

import it.mondovespapx.dao.CategoriaDAO;
import it.mondovespapx.dao.ProdottoDAO;
import it.mondovespapx.model.Categoria;
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

//Servlet che gestisce la pagina del catalogo
@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Crea gli oggetti per le query al database
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        
        //Cerca la categoria selezionata nell'URL
        String categoriaParam = request.getParameter("categoria");

        try {
            List<Prodotto> prodotti;
            //Se nell'URL è presente un ID categoria
            if (categoriaParam != null && !categoriaParam.isEmpty()) {
                int idCategoria = Integer.parseInt(categoriaParam);
                prodotti = prodottoDAO.getProdottiByCategoria(idCategoria);
            } else {
                //Se non c'è nessuna categoria carica tutti i prodotti
                prodotti = prodottoDAO.getAllProdotti();
            }

            //Carica la lista di tutte le categorie
            List<Categoria> categorie = categoriaDAO.getAllCategorie();
            //Rende le due liste disponibili alla pagina web
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("categorie", categorie);

        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //Passa il controllo e i dati al file catalogo.jsp per generare l'HTML
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/catalogo.jsp");
        rd.forward(request, response);
    }
}