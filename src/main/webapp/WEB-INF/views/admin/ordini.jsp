<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Ordine" %>
<%@ page import="it.mondovespapx.model.DettaglioOrdine" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestione Ordini - Admin - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<header>
    <h1>Pannello Admin - MondoVespaPX</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/ordini">Gestione Ordini</a>
        <a href="${pageContext.request.contextPath}/logout">Esci</a>
    </nav>
</header>
<main>
    <h2>Gestione Ordini</h2>

    <div class="filtri-ordini">
        <form action="${pageContext.request.contextPath}/admin/ordini" method="GET">
            <label for="dataInizio">Da:</label>
            <input type="date" id="dataInizio" name="dataInizio" value="<%= request.getAttribute("dataInizio") %>">            
            <label for="dataFine">A:</label>
            <input type="date" id="dataFine" name="dataFine" value="<%= request.getAttribute("dataFine") %>">            
            <label for="cliente">Cliente (Email/Nome):</label>
            <input type="text" id="cliente" name="cliente" value="<%= request.getAttribute("cliente") %>">           
            <button type="submit">Filtra</button>
            <a href="${pageContext.request.contextPath}/admin/ordini"><button type="button">Reset</button></a>
        </form>
    </div>
<%
    //Recupera l'elenco degli ordini completi di dettagli passati dalla servlet
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
    if (ordini == null || ordini.isEmpty()) {
%>
    <p>Nessun ordine trovato.</p>
<%
    } else {
        for (Ordine o : ordini) {
            //Estrazione corretta: chiedo i dettagli all'oggetto, non al database!
            List<DettaglioOrdine> dettagli = o.getDettagli();
%>
    <div class="ordine" style="border: 1px solid #ccc; padding: 15px; margin-bottom: 20px;">
        <h3>Ordine #<%= o.getId() %> - Cliente: <%= o.getNomeUtente() %></h3>
        <p>Data: <%= o.getDataOrdine() %></p>        
        <p>Metodo di pagamento: <%= o.getMetodoPagamento() %></p>     
        <p>Indirizzo spedizione: <%= o.getIndirizzoSpedizione() %></p>
        <p>Totale: <%= o.getTotale() %> €</p>
        <table class="tabella-dettagli" style="width: 100%; text-align: left; margin-bottom: 15px;">
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
            <tr><td colspan="3">Nessun dettaglio trovato per questo ordine.</td></tr>
            <%
                }
            %>
        </table>
        <form action="${pageContext.request.contextPath}/admin/ordini" method="POST" style="background: #f9f9f9; padding: 10px;">
            <input type="hidden" name="idOrdine" value="<%= o.getId() %>">
            <label for="stato_<%= o.getId() %>">Stato Attuale: <strong><%= o.getStato() %></strong>. Modifica in:</label>
			<select name="stato" id="stato_<%= o.getId() %>">
       			 <option value="in attesa" <%= "in attesa".equals(o.getStato()) ? "selected" : "" %>>In attesa</option>
       			 <option value="confermato" <%= "confermato".equals(o.getStato()) ? "selected" : "" %>>Confermato</option>
      			  <option value="spedito" <%= "spedito".equals(o.getStato()) ? "selected" : "" %>>Spedito</option>
        		<option value="consegnato" <%= "consegnato".equals(o.getStato()) ? "selected" : "" %>>Consegnato</option>
       			 <option value="annullato" <%= "annullato".equals(o.getStato()) ? "selected" : "" %>>Annullato</option>
    </select>
            <button type="submit">Aggiorna Stato</button>
        </form>
    </div>
<%
        }
    }
%>
</main>
<footer>
    <p>MondoVespaPX - Pannello Amministrazione</p>
</footer>
</body>
</html>