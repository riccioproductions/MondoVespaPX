<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Ordine" %>
<%@ page import="it.mondovespapx.model.Utente" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>I miei ordini - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<header>
    <h1>MondoVespaPX</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        <%
            Utente utente = (Utente) session.getAttribute("utente");
            if (utente != null) {
        %>
            <a href="${pageContext.request.contextPath}/area-utente/profilo"><%= utente.getNome() %></a>
            <a href="${pageContext.request.contextPath}/logout">Esci</a>
        <%
            } else {
        %>
            <a href="${pageContext.request.contextPath}/login">Accedi</a>
            <a href="${pageContext.request.contextPath}/registrazione">Registrati</a>
        <%
            }
        %>
    </nav>
</header>
<main>
    <h2>I miei ordini</h2>
    <%
        //Recupera l'elenco degli ordini passato dalla Servlet
        List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");  
        //Controlla se la lista è vuota o non esiste
        if (ordini == null || ordini.isEmpty()) {
    %>
        <p>Non hai ancora effettuato ordini.</p>
        <a href="${pageContext.request.contextPath}/catalogo">Vai al catalogo</a>
    <%
        } else {
            for (Ordine o : ordini) {
    %>
        <div class="ordine">
            <%--Estrae e stampa i parametri dell'ordine tramite i metodi getter--%>
            <p><strong>Ordine #<%= o.getId() %></strong></p>
            <p>Data: <%= o.getDataOrdine() %></p>
            <p>Stato: <%= o.getStato() %></p>
            <p>Totale: <%= o.getTotale() %> €</p>
        </div>
    <%
            } 
        } 
    %>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>
</body>
</html>