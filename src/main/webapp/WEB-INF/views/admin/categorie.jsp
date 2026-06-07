<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Categoria" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione categorie - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<header>
    <h1>MondoVespaPX — Admin</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/prodotti">Gestione prodotti</a>
        <a href="${pageContext.request.contextPath}/admin/categorie">Gestione categorie</a>
        <a href="${pageContext.request.contextPath}/admin/ordini">Gestione ordini</a>
        <a href="${pageContext.request.contextPath}/logout">Esci</a>
    </nav>
</header>
<main>
    <h2>Gestione categorie</h2>
    <h3>Inserisci nuova categoria</h3>
    <form action="${pageContext.request.contextPath}/admin/categorie" method="post">
        <input type="hidden" name="azione" value="inserisci">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>
        <label for="descrizione">Descrizione:</label>
        <textarea id="descrizione" name="descrizione"></textarea>
        <button type="submit">Inserisci</button>
    </form>
    <h3>Categorie esistenti</h3>
    <%
        List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
        for (Categoria c : categorie) {
    %>
        <div class="ordine">
            <p><strong><%= c.getNome() %></strong></p>
            <p><%= c.getDescrizione() != null ? c.getDescrizione() : "" %></p>
            <form action="${pageContext.request.contextPath}/admin/categorie" method="post">
                <input type="hidden" name="azione" value="modifica">
                <input type="hidden" name="id" value="<%= c.getId() %>">
                <label>Nome:</label>
                <input type="text" name="nome" value="<%= c.getNome() %>" required>
                <label>Descrizione:</label>
                <textarea name="descrizione"><%= c.getDescrizione() != null ? c.getDescrizione() : "" %></textarea>
                <button type="submit">Salva</button>
            </form>
            <form action="${pageContext.request.contextPath}/admin/categorie" method="post">
                <input type="hidden" name="azione" value="elimina">
                <input type="hidden" name="id" value="<%= c.getId() %>">
                <button type="submit">Elimina</button>
            </form>
        </div>
    <%
        }
    %>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>
</body>
</html>