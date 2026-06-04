<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.ElementoCarrello" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Riepilogo ordine - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
    <h1>MondoVespaPX</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        <a href="${pageContext.request.contextPath}/logout">Esci</a>
    </nav>
</header>
<main>
    <h2>Riepilogo ordine</h2>

    <%
        //Recupera la lista dei prodotti dal carrello e il costo totale passati dalla Servlet
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

    <form action="${pageContext.request.contextPath}/area-utente/ordine" method="post">
        <button type="submit">Conferma ordine</button>
    </form>

    <a href="${pageContext.request.contextPath}/carrello">Torna al carrello</a>
</main>

<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>