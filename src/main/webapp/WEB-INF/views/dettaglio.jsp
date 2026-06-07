<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.mondovespapx.model.Prodotto" %>
<%@ page import="it.mondovespapx.model.Utente" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dettaglio prodotto - MondoVespaPX</title>
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
    <%
        //Recupera l'oggetto passato dalla Servlet
        Prodotto p = (Prodotto) request.getAttribute("prodotto");
    %>

    <%--Stampa i dati del prodotto leggendoli dai metodi getters--%>
    <h2><%= p.getNome() %></h2>
    <% if (p.getImmagine() != null) { %>
    <img src="${pageContext.request.contextPath}/images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" width="300"> <% } %>
    <p><%= p.getDescrizione() %></p>
    <p>Prezzo: <%= p.getPrezzo() %> €</p>
    <p>Disponibilità: <%= p.getQuantitaDisponibile() %> pezzi</p>

    <form action="${pageContext.request.contextPath}/carrello" method="post">
        
        <input type="hidden" name="idProdotto" value="<%= p.getId() %>">
        <input type="hidden" name="nomeProdotto" value="<%= p.getNome() %>">
        <input type="hidden" name="prezzo" value="<%= p.getPrezzo() %>">
        <label for="quantita">Quantità:</label>
        <input type="number" id="quantita" name="quantita" value="1" min="1" max="<%= p.getQuantitaDisponibile() %>">
        <button type="submit">Aggiungi al carrello</button>
    </form>
    <a href="${pageContext.request.contextPath}/catalogo">Torna al catalogo</a>
</main>

<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>