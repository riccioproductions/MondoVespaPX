<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Prodotto" %>
<%@ page import="it.mondovespapx.model.Categoria" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catalogo - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header>
    <h1>MondoVespaPX</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/catalogo">Tutti i prodotti</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
        <a href="${pageContext.request.contextPath}/login">Accedi</a>
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

    <div class="prodotti">
        <%
            //Recupera la lista dei prodotti passata dalla Servlet
            List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
            if (prodotti == null || prodotti.isEmpty()) {
        %>
            <p>Nessun prodotto disponibile.</p>
        <%
            } else {
                for (Prodotto p : prodotti) {
        %>
            <div class="prodotto">
                <h3><%= p.getNome() %></h3>
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