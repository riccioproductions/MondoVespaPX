<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.ElementoCarrello" %>
<%@ page import="it.mondovespapx.model.Utente" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrello - MondoVespaPX</title>
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
    <h2>Il tuo carrello</h2>
    <%
        //Recupera la lista degli elementi e il totale passati dalla Servlet
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) request.getAttribute("carrello");
        double totale = (double) request.getAttribute("totale");

        if (carrello == null || carrello.isEmpty()) {
    %>
        <p>Il carrello è vuoto.</p>
        <a href="${pageContext.request.contextPath}/catalogo">Torna al catalogo</a>
    <%
        } else {
            //Stampa ogni singolo prodotto presente nella lista
            for (ElementoCarrello e : carrello) {
    %>
        <div class="elemento-carrello">
            <p><strong><%= e.getNomeProdotto() %></strong></p>
            <p>Prezzo unitario: <%= e.getPrezzo() %> €</p>

            <form action="${pageContext.request.contextPath}/carrello" method="post">
                <input type="hidden" name="azione" value="aggiorna">
                <input type="hidden" name="idProdotto" value="<%= e.getIdProdotto() %>">
                
                <label for="quantita_<%= e.getIdProdotto() %>">Quantità:</label>
                <input type="number" id="quantita_<%= e.getIdProdotto() %>"
                       name="quantita" value="<%= e.getQuantita() %>" min="1">
                <button type="submit">Aggiorna</button>
            </form>

            <p>Totale: <%= e.getTotale() %> €</p>

            <form action="${pageContext.request.contextPath}/carrello" method="post">
                <input type="hidden" name="azione" value="rimuovi">
                <input type="hidden" name="idProdotto" value="<%= e.getIdProdotto() %>">
                <button type="submit">Rimuovi</button>
            </form>
        </div>
    <%
            } 
    %>
        <p><strong>Totale carrello: <%= totale %> €</strong></p>

        <form action="${pageContext.request.contextPath}/carrello" method="post">
            <input type="hidden" name="azione" value="svuota">
            <button type="submit">Svuota carrello</button>
        </form>

        <a href="${pageContext.request.contextPath}/area-utente/ordine">Procedi all'acquisto</a>
    <%
        } 
    %>
</main>

<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>