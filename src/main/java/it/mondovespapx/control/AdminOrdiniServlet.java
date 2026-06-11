package it.mondovespapx.control;

import it.mondovespapx.dao.OrdineDAO;
import it.mondovespapx.model.Ordine;
import it.mondovespapx.model.DettaglioOrdine;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// Servlet dedicata all'amministrazione degli ordini
@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {       
        String dataInizio = request.getParameter("dataInizio");
        String dataFine = request.getParameter("dataFine");
        String cliente = request.getParameter("cliente");
        OrdineDAO dao = new OrdineDAO();
        try {
            //Recupera la lista degli ordini
            List<Ordine> ordini = dao.getOrdiniFiltrati(dataInizio, dataFine, cliente);        
            //Popola ogni ordine con i suoi dettagli specifici
            for (Ordine o : ordini) {
                List<DettaglioOrdine> dettagli = dao.getDettagliByOrdine(o.getId());
                o.setDettagli(dettagli);
            }
            request.setAttribute("ordini", ordini);            
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
        request.setAttribute("dataInizio", dataInizio != null ? dataInizio : "");
        request.setAttribute("dataFine", dataFine != null ? dataFine : "");
        request.setAttribute("cliente", cliente != null ? cliente : "");
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/ordini.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {        
        int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
        String stato = request.getParameter("stato");
        OrdineDAO dao = new OrdineDAO();       
        try {
            dao.aggiornaStato(idOrdine, stato);
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
        response.sendRedirect(request.getContextPath() + "/admin/ordini");
    }
}