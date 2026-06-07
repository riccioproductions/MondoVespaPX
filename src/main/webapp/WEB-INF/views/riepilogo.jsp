<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.ElementoCarrello" %>
<%@ page import="it.mondovespapx.model.Utente" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Riepilogo ordine - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <script src="${pageContext.request.contextPath}/scripts/validazione.js" defer></script>
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
    <h2>Riepilogo ordine</h2>

    <%
        //Estrae il carrello e il totale preparati dalla Servlet nella request
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) request.getAttribute("carrello");
        double totale = (double) request.getAttribute("totale");
        for (ElementoCarrello e : carrello) {
    %>
        <div class="elemento-carrello">
            <p><strong><%= e.getNomeProdotto() %></strong></p>
            <p>Quantità: <%= e.getQuantita() %></p>
            <p>Prezzo unitario: <%= e.getPrezzo() %> €</p>
            <p>Totale: <%= e.getTotale() %> €</p>
        </div>
    <%
        } 
    %>

    <p><strong>Totale ordine: <%= totale %> €</strong></p>

    <h3>Informazioni spedizione e pagamento</h3>

    <form id="formOrdine" action="${pageContext.request.contextPath}/area-utente/ordine" method="post"
          onsubmit="return validaOrdine()">
        <label for="indirizzo">Indirizzo di spedizione:</label>
        <input type="text" id="indirizzo" name="indirizzo"
               value="<%= utente.getIndirizzo() != null ? utente.getIndirizzo() : "" %>">
        <span class="errore" id="erroreIndirizzo"></span>
        <label for="metodoPagamento">Metodo di pagamento:</label>
        <select id="metodoPagamento" name="metodoPagamento">
            <option value="">-- Seleziona --</option>
            <option value="carta" <%= "carta".equals(utente.getMetodoPagamento()) ? "selected" : "" %>>Carta di credito</option>
            <option value="paypal" <%= "paypal".equals(utente.getMetodoPagamento()) ? "selected" : "" %>>PayPal</option>
            <option value="bonifico" <%= "bonifico".equals(utente.getMetodoPagamento()) ? "selected" : "" %>>Bonifico bancario</option>
        </select>
        <span class="errore" id="erroreMetodo"></span>
        <button type="submit">Conferma ordine</button>
    </form>
    <a href="${pageContext.request.contextPath}/carrello">Torna al carrello</a>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>