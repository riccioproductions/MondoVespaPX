package it.mondovespapx.control;

import it.mondovespapx.dao.UtenteDAO;
import it.mondovespapx.model.Utente;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//Servlet che gestisce la creazione di un nuovo account
@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/registrazione.jsp");
        rd.forward(request, response);
    }

    //Viene eseguito quando l'utente compila il modulo e clicca su "Registrati"
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Estrae i dati inviati dal client
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //Crea l'oggetto DAO per comunicare con la tabella utenti del database
        UtenteDAO dao = new UtenteDAO();
        try {
            //verifica se l'indirizzo email è già presente nel DB
            if (dao.emailEsiste(email)) {
                request.setAttribute("errore", "Email già registrata");
                //Ricarica la pagina di registrazione mostrando l'errore
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/registrazione.jsp");
                rd.forward(request, response);
                return;
            }

            //Compone il nuovo utente inserendo i dati raccolti dal form
            Utente u = new Utente();
            u.setNome(nome);
            u.setCognome(cognome);
            u.setEmail(email);
            u.setPassword(password);
            dao.registra(u);
            response.sendRedirect(request.getContextPath() + "/login");
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
    }
}