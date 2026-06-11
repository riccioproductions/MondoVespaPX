<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Ordine" %>
<%@ page import="it.mondovespapx.model.Utente" %>
<%@ page import="it.mondovespapx.model.DettaglioOrdine" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>I miei ordini - MondoVespaPX</title>
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
    <h2>I miei ordini</h2>
<%
    //Recupera l'elenco degli ordini passato dalla servlet
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
    
    if (ordini == null || ordini.isEmpty()) {
%>
    <p>Non hai ancora effettuato ordini.</p>
    <a href="${pageContext.request.contextPath}/catalogo">Vai al catalogo</a>
<%
    } else {
        for (Ordine o : ordini) {
            List<DettaglioOrdine> dettagli = o.getDettagli();
%>
    <div class="ordine">
        <p><strong>Ordine #<%= o.getId() %></strong></p>
        <p>Data: <%= o.getDataOrdine() %></p>
        <p>Stato: <strong><%= o.getStato() %></strong></p>
        <p>Metodo di pagamento: <%= o.getMetodoPagamento() %></p>     
        <p>Indirizzo spedizione: <%= o.getIndirizzoSpedizione() %></p>
        <p>Totale: <%= o.getTotale() %> €</p>
        <table class="tabella-dettagli">
            <tr>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Prezzo unitario</th>
            </tr>
            <%
                if (dettagli != null) {
                    for (DettaglioOrdine d : dettagli) {
            %>
            <tr>
                <td><%= d.getNomeProdotto() != null ? d.getNomeProdotto() : "Prodotto eliminato" %></td>
                <td><%= d.getQuantita() %></td>
                <td><%= d.getPrezzoUnitario() %> €</td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr><td colspan="3">Dettagli non disponibili.</td></tr>
            <%
                }
            %>
        </table>
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