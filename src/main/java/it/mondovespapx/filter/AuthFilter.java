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

//Filtro che intercetta tutte le richieste HTTP sotto "/area-utente/"
@WebFilter("/area-utente/*")
public class AuthFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //Recupera la sessione legata alla richiesta. false per evitare di creare una nuova sessione vuota nel caso in cui non ne esista una attiva
        HttpSession session = req.getSession(false);
        String token = null;

        //Se esiste una sessione attiva, estrae l'attributo "token"
        if (session != null) {
            token = (String) session.getAttribute("token");
        }
        if (token == null) {
            //Respinge la richiesta e reindirizza il client verso la pagina di login
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        chain.doFilter(request, response);
    }
    public void destroy() {}
}