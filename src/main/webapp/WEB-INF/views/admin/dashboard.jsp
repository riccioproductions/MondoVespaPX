<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Ordine" %>
<%@ page import="it.mondovespapx.model.Prodotto" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Admin - MondoVespaPX</title>
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
    <h2>Dashboard</h2>

    <%
        //Recupera dalla request le liste popolate dalla AdminDashboardServlet
        List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
        List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
    %>
    <p>Prodotti nel catalogo: <strong><%= prodotti.size() %></strong></p>
    <p>Ordini totali: <strong><%= ordini.size() %></strong></p>
    <a href="${pageContext.request.contextPath}/admin/prodotti">Gestisci prodotti</a>
    <a href="${pageContext.request.contextPath}/admin/categorie">Gestisci categorie</a>
    <a href="${pageContext.request.contextPath}/admin/ordini">Gestisci ordini</a>
</main>

<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>