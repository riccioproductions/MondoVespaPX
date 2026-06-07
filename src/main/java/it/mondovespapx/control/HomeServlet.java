package it.mondovespapx.control;

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

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try {
            List<Prodotto> prodotti = prodottoDAO.getAllProdotti();
            List<Categoria> categorie = categoriaDAO.getAllCategorie();
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("categorie", categorie);
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
        rd.forward(request, response);
    }
}