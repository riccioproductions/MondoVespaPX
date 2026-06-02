package it.mondovespapx.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//Servlet che gestisce il logout
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recupera la sessione corrente dell'utente solo se esiste già 
        //il parametro 'false' impedisce al server di crearne una nuova per sbaglio
        HttpSession session = request.getSession(false);
        //Se l'utente ha effettivamente una sessione attiva in questo momento la distrugge con invalidate()
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/catalogo");
    }
}