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

//Servlet dedicata alla gestione dei prodotti lato admin
@WebServlet("/admin/prodotti")
public class AdminProdottoServlet extends HttpServlet {
    //Recupera i dati necessari e mostra la pagina di gestione dei prodotti
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Inizializza i DAO per interrogare il database
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try {
            //Recupera l'elenco completo dei prodotti e delle categorie disponibili
            List<Prodotto> prodotti = prodottoDAO.getAllProdotti();
            List<Categoria> categorie = categoriaDAO.getAllCategorie();
            
            //Passa le liste alla request per renderle accessibili alla pagina JSP
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("categorie", categorie);
            
        } catch (SQLException e) {
           throw new ServletException("Errore database", e);
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/prodotti.jsp");
        rd.forward(request, response);
    }

    //inserire, modificare o eliminare un prodotto
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Legge il parametro azione per capire quale operazione eseguire
        String azione = request.getParameter("azione");
        ProdottoDAO dao = new ProdottoDAO();

        try {
            //creazione di un nuovo prodotto
            if ("inserisci".equals(azione)) {
                Prodotto p = new Prodotto();
                p.setNome(request.getParameter("nome"));
                p.setDescrizione(request.getParameter("descrizione"));
                p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
                p.setQuantitaDisponibile(Integer.parseInt(request.getParameter("quantita")));
                p.setImmagine(request.getParameter("immagine"));
                p.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
                dao.inserisci(p);

            //aggiornamento di un prodotto esistente
            } else if ("modifica".equals(azione)) {
                Prodotto p = new Prodotto();

                p.setId(Integer.parseInt(request.getParameter("id")));
                p.setNome(request.getParameter("nome"));
                p.setDescrizione(request.getParameter("descrizione"));
                p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
                p.setQuantitaDisponibile(Integer.parseInt(request.getParameter("quantita")));
                p.setImmagine(request.getParameter("immagine"));
                p.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));

                dao.aggiorna(p);

            //rimozione di un prodotto
            } else if ("elimina".equals(azione)) {
                int id = Integer.parseInt(request.getParameter("id"));

                dao.elimina(id);
            }

        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //esegue un redirect a "/admin/prodotti"   
        response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    }
}