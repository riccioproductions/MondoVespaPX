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
    <title>MondoVespaPX - Ricambi Vespa PX</title>
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

    <section class="hero">
        <h2>Ricambi originali per la tua Vespa PX</h2>
        <p>Tutto quello che ti serve per mantenere, restaurare ed elaborare la tua Vespa PX.</p>
        <a href="${pageContext.request.contextPath}/catalogo" class="btn-hero">Vai al catalogo</a>
    </section>
    <section class="sezione-categorie">
        <h2>Categorie</h2>
        <div class="categorie">
            <%
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
    </section>
    <section class="sezione-prodotti">
        <h2>Prodotti in evidenza</h2>
        <div class="prodotti">
            <%
                List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
                if (prodotti != null) {
                    int count = 0;
                    for (Prodotto p : prodotti) {
                        if (count >= 3) break;
            %>
                <div class="prodotto">
                    <h3><%= p.getNome() %></h3>
                    <% if (p.getImmagine() != null) { %>
    				<img src="${pageContext.request.contextPath}/images/<%= p.getImmagine() %>" alt="<%= p.getNome() %>" width="200"><% } %>
                    <p><%= p.getDescrizione() %></p>
                    <p>Prezzo: <%= p.getPrezzo() %> €</p>
                    <a href="${pageContext.request.contextPath}/prodotto?id=<%= p.getId() %>">Dettagli</a>
                </div>
            <%
                        count++;
                    }
                }
            %>
        </div>
    </section>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>