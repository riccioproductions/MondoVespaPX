package it.mondovespapx.controller;

import it.mondovespapx.dao.UtenteDAO;
import it.mondovespapx.model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

//Servlet dedicata alla gestione del login
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
        rd.forward(request, response);
    }

    //Viene richiamato quando l'utente invia i dati
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recupera le credenziali digitate nel form HTML
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //Prepara il DAO per comunicare con il database
        UtenteDAO dao = new UtenteDAO();
        try {
            //Tenta l'autenticazione. Se fallisce, 'u' sarà null
            Utente u = dao.login(email, password);
            if (u != null) {
                //Avvia la sessione per mantenere l'utente connesso durante la navigazione
                HttpSession session = request.getSession();
                session.setAttribute("utente", u);
                //Controllo degli accessi in base al ruolo
                if ("admin".equals(u.getRuolo())) {
                    //Gli amministratori vengono reindirizzati al loro pannello di controllo
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                } else {
                    //I clienti vengono inviati al catalogo prodotti
                    response.sendRedirect(request.getContextPath() + "/catalogo");
                }
            //Se le credenziali sono errate
            } else {
                request.setAttribute("errore", "Email o password errati");
                //Ricarica la stessa pagina di login in modo che l'utente possa riprovare mostrando il messaggio d'errore
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
    }
}