<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.ElementoCarrello" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrello - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header>
    <h1>MondoVespaPX</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        <a href="${pageContext.request.contextPath}/login">Accedi</a>
    </nav>
</header>

<main>
    <h2>Il tuo carrello</h2>
    <%
        //Recupera la lista dei prodotti nel carrello e il costo totale passati dalla Servlet
        List<ElementoCarrello> carrello = (List<ElementoCarrello>) request.getAttribute("carrello");
        double totale = (double) request.getAttribute("totale");

        if (carrello == null || carrello.isEmpty()) {
    %>
        <p>Il carrello è vuoto.</p>
        <a href="${pageContext.request.contextPath}/catalogo">Torna al catalogo</a>
    <%
        } else {
            for (ElementoCarrello e : carrello) {
    %>
        <div class="elemento-carrello">
            
            <%--Legge e stampa i dettagli dell'elemento corrente--%>
            <p><strong><%= e.getNomeProdotto() %></strong></p>
            <p>Prezzo unitario: <%= e.getPrezzo() %> €</p>
            <p>Quantità: <%= e.getQuantita() %></p>
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
        
        <a href="${pageContext.request.contextPath}/ordine">Procedi all'acquisto</a>
    <%
        } 
    %>
</main>

<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>