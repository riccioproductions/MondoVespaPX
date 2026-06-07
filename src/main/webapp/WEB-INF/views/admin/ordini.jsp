<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Ordine" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione ordini - MondoVespaPX</title>
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
    <h2>Gestione ordini</h2>
    <h3>Filtra ordini</h3>
    <form action="${pageContext.request.contextPath}/admin/ordini" method="get"> 
        <label for="dataInizio">Dal:</label>
        <input type="date" id="dataInizio" name="dataInizio" value="${dataInizio}">
        <label for="dataFine">Al:</label>
        <input type="date" id="dataFine" name="dataFine" value="${dataFine}">
        <label for="cliente">Cliente (nome, cognome o email):</label>
        <input type="text" id="cliente" name="cliente" value="${cliente}">
        <button type="submit">Filtra</button>        
        <a href="${pageContext.request.contextPath}/admin/ordini">Reimposta</a>
    </form>
    <h3>Ordini</h3>
    <%
        //Recupera dalla request la lista degl iordini
        List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");        
        //Verifica se la query ha restituito zero risultati
        if (ordini == null || ordini.isEmpty()) {
    %>
        <p>Nessun ordine trovato.</p>
    <%
        } else {
            for (Ordine o : ordini) {
    %>
        <div class="ordine">
            <%-- Stampa i dettagli anagrafici e contabili --%>
            <p><strong>Ordine #<%= o.getId() %></strong></p>
            <p>Cliente: <%= o.getNomeUtente() %></p>
            <p>Data: <%= o.getDataOrdine() %></p>
            <p>Totale: <%= o.getTotale() %> €</p>
            <p>Stato attuale: <strong><%= o.getStato() %></strong></p>
            <form action="${pageContext.request.contextPath}/admin/ordini" method="post">             
                <input type="hidden" name="idOrdine" value="<%= o.getId() %>">              
                <select name="stato">
                    <%-- Verifica lo stato attuale nel DB per inserire l'attributo selected sull'opzione corretta --%>
                    <option value="in attesa" <%= "in attesa".equals(o.getStato()) ? "selected" : "" %>>In attesa</option>
                    <option value="confermato" <%= "confermato".equals(o.getStato()) ? "selected" : "" %>>Confermato</option>
                    <option value="spedito" <%= "spedito".equals(o.getStato()) ? "selected" : "" %>>Spedito</option>
                    <option value="consegnato" <%= "consegnato".equals(o.getStato()) ? "selected" : "" %>>Consegnato</option>
                </select>              
                <button type="submit">Aggiorna stato</button>
            </form>
        </div>
    <%
            } 
        } 
    %>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>
</body>
</html>