<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Ordine" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione ordini - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
    <h1>MondoVespaPX — Admin</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/prodotti">Gestione prodotti</a>
        <a href="${pageContext.request.contextPath}/admin/ordini">Gestione ordini</a>
        <a href="${pageContext.request.contextPath}/logout">Esci</a>
    </nav>
</header>

<main>
    <h2>Gestione ordini</h2>
    <%
        //Recupera l'elenco degli ordini passato dalla AdminOrdiniServlet
        List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
        if (ordini == null || ordini.isEmpty()) {
    %>
        <p>Nessun ordine ricevuto.</p>
    <%
        } else {
            for (Ordine o : ordini) {
    %>
        <div class="ordine">
            
            <%-- Estrae i dati dell'ordine --%>
            <p><strong>Ordine #<%= o.getId() %></strong></p>
            <p>Cliente: <%= o.getNomeUtente() %></p>
            <p>Data: <%= o.getDataOrdine() %></p>
            <p>Totale: <%= o.getTotale() %> €</p>
            <p>Stato attuale: <strong><%= o.getStato() %></strong></p>

            <form action="${pageContext.request.contextPath}/admin/ordini" method="post">
                <input type="hidden" name="idOrdine" value="<%= o.getId() %>">
                <select name="stato">
    <option value="in attesa" <% if ("in attesa".equals(o.getStato())) { out.print("selected"); } else { out.print(""); } %>>In attesa</option>
    <option value="confermato" <% if ("confermato".equals(o.getStato())) { out.print("selected"); } else { out.print(""); } %>>Confermato</option>
    <option value="spedito" <% if ("spedito".equals(o.getStato())) { out.print("selected"); } else { out.print(""); } %>>Spedito</option>
    <option value="consegnato" <% if ("consegnato".equals(o.getStato())) { out.print("selected"); } else { out.print(""); } %>>Consegnato</option>
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