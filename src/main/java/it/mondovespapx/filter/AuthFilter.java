package it.mondovespapx.filter;

import it.mondovespapx.model.Utente;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

//Filtro per il login
@WebFilter("/area-utente/*")
public class AuthFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {}
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //Recupera la sessione corrente solo se true
        HttpSession session = req.getSession(false);
        Utente utente = null;
        
        //Se l'utente ha una sessione attiva, estrae l'oggetto utente dal login
        if (session != null) {
            utente = (Utente) session.getAttribute("utente");
        }
        //Se l'oggetto utente è null (no login oppure sessione scaduta) viene reindirizzato al login
        if (utente == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        //Consente l'accesso
        chain.doFilter(request, response);
    }
    public void destroy() {}
}