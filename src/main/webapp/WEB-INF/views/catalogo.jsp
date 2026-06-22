<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Prodotto" %>
<%@ page import="it.mondovespapx.model.Categoria" %>
<%@ page import="it.mondovespapx.model.Utente" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalogo - MondoVespaPX</title>
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
    <h2>Catalogo prodotti</h2>

    <div class="categorie">
        <strong>Filtra per categoria:</strong>
        <%
            //Recupera la lista delle categorie passata dalla Servlet
            List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
            if (categorie != null) {
                for (Categoria c : categorie) {
        %>
            <a href="${pageContext.request.contextPath}/catalogo?categoria=<%= c.getId() %>">
                <%= c.getNome() %>
            </a>
        <%
                }
            }
        %>
    </div>

<%
    Categoria cat = (Categoria) request.getAttribute("categoriaSelezionata");
    if (cat != null) {
%>
    <div class="header-categoria">
        <h1><%= cat.getNome() %></h1>
        <p class="descrizione-categoria"><%= cat.getDescrizione() %></p>
    </div>
<% 
} 
%>

<div class="prodotti">
        <%
            List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
            //Controllo lista prodotti
            if (prodotti == null || prodotti.isEmpty()) {
        %>
            <p>Nessun prodotto disponibile.</p>
        <%
            } else {
                //Stampa dei prodotti
                for (Prodotto p : prodotti) {
        %>
            <div class="prodotto">
                <h3><%= p.getNome() %></h3>
                <% if (p.getImmagine() != null) { %>
                    <img src="${pageContext.request.contextPath}/images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" width="200">
                <% } %>
                <p><%= p.getDescrizione() %></p>
                <p>Prezzo: <%= p.getPrezzo() %> €</p>
                <a href="${pageContext.request.contextPath}/prodotto?id=<%= p.getId() %>">Dettagli</a>
            </div>
        <%
                } 
            }
        %>
    </div>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>