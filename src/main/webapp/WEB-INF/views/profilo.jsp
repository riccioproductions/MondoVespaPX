<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="it.mondovespapx.model.Utente" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profilo - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<header>
    <h1>MondoVespaPX</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        <a href="${pageContext.request.contextPath}/area-utente/ordini">I miei ordini</a>
        <a href="${pageContext.request.contextPath}/logout">Esci</a>
    </nav>
</header>
<main>
    <h2>Il mio profilo</h2>
    <%--Controlla se la Servlet ha inviato un messaggio di conferma--%>
    <% if (request.getAttribute("messaggio") != null) { %>
        <p class="successo"><%= request.getAttribute("messaggio") %></p>
    <% } %>
    <%
        //Estrae l'oggetto utente con i dati attuali passati dalla Servlet
        Utente utente = (Utente) request.getAttribute("utente");
    %>
    <form action="${pageContext.request.contextPath}/area-utente/profilo" method="post">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="<%= utente.getNome() %>" required>
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" value="<%= utente.getCognome() %>" required>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="<%= utente.getEmail() %>" required>
        <label for="indirizzo">Indirizzo:</label>
        <input type="text" id="indirizzo" name="indirizzo" value="<%= utente.getIndirizzo() != null ? utente.getIndirizzo() : "" %>">
        <label for="metodoPagamento">Metodo di pagamento:</label>
        <select id="metodoPagamento" name="metodoPagamento">
            <option value="carta" <%= "carta".equals(utente.getMetodoPagamento()) ? "selected" : "" %>>Carta di credito</option>
            <option value="paypal" <%= "paypal".equals(utente.getMetodoPagamento()) ? "selected" : "" %>>PayPal</option>
            <option value="bonifico" <%= "bonifico".equals(utente.getMetodoPagamento()) ? "selected" : "" %>>Bonifico bancario</option>
        </select>
        <button type="submit">Salva modifiche</button>
    </form>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>
</body>
</html>