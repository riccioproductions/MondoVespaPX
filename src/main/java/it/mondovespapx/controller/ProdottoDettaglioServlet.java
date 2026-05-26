package it.mondovespapx.controller;

import it.mondovespapx.dao.ProdottoDAO;
import it.mondovespapx.model.Prodotto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//Servlet che gestisce la visualizzazione di un singolo prodotto
@WebServlet("/prodotto")
public class ProdottoDettaglioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            //Rimanda l'utente alla pagina principale del catalogo
            response.sendRedirect(request.getContextPath() + "/catalogo");
            return;
        }

        //Crea l'oggetto per comunicare col database
        ProdottoDAO dao = new ProdottoDAO();

        try {
            int id = Integer.parseInt(idParam);
            Prodotto p = dao.getProdottoById(id);
            if (p == null) {
                response.sendRedirect(request.getContextPath() + "/catalogo");
                return;
            }
            //Salva il prodotto trovato per passarlo alla pagina web
            request.setAttribute("prodotto", p);

        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //Invia i dati alla pagina dettaglio.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/dettaglio.jsp");
        rd.forward(request, response);
    }
}