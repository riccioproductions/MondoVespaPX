package it.mondovespapx.control;

import it.mondovespapx.dao.CategoriaDAO;
import it.mondovespapx.model.Categoria;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//Gestione delle categorie
@WebServlet("/admin/categorie")
public class AdminCategoriaServlet extends HttpServlet {

	//Recupera i dati
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoriaDAO dao = new CategoriaDAO();
        try {
            //Richiede al DAO il recupero della lista categoria dal database
            List<Categoria> categorie = dao.getAllCategorie();            
            //Inserisce la lista recuperata come attributo all'interno della request per farla vedere alla jsp
            request.setAttribute("categorie", categorie);
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
        //Passa il controllo alla jsp
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/categorie.jsp");
        rd.forward(request, response);
    }

    //Metodo che gestisce le azioni sulle categorie
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Estrae il parametro "azione dalla richiesta.
        String azione = request.getParameter("azione");
        CategoriaDAO dao = new CategoriaDAO();
        try {
            if ("inserisci".equals(azione)) {    
                Categoria c = new Categoria();
                //Inserisce i parametri ricevuti dalla request come attributi dell'oggetto
                c.setNome(request.getParameter("nome"));
                c.setDescrizione(request.getParameter("descrizione"));
                dao.inserisci(c);
            } else if ("modifica".equals(azione)) {               
                Categoria c = new Categoria();
                //Trova l'id della categoria e la aggiorna
                c.setId(Integer.parseInt(request.getParameter("id")));
                c.setNome(request.getParameter("nome"));
                c.setDescrizione(request.getParameter("descrizione"));
                dao.aggiorna(c);
            } else if ("elimina".equals(azione)) {
                //Elimina la categoria corrispondente all'id
                int id = Integer.parseInt(request.getParameter("id"));
                dao.elimina(id);
            }
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
        //Ricarica la pagina per prevenire doppi inserimenti
        response.sendRedirect(request.getContextPath() + "/admin/categorie");
    }
}