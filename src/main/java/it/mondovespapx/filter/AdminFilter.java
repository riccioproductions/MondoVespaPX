package it.mondovespapx.filter;

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

//Filtro di sicurezza per le pagine di amministrazione
@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    //ogni singola richiesta verso gli URL "/admin/*"
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //Recupera la sessione attiva ed evita di crearne una nuova in memoria se non esiste
        HttpSession session = req.getSession(false);
        String tokenAdmin = null;
        //Se la sessione è valida, estrae il token per l'admin
        if (session != null) {
            tokenAdmin = (String) session.getAttribute("tokenAdmin");
        }
        //Se la sessione è scaduta o l'utente non ha il token da admin
        if (tokenAdmin == null) {
            //Nega l'accesso e reindirizza verso il form di login
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
    public void destroy() {}
}