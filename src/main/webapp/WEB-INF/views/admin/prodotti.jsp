<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="it.mondovespapx.model.Prodotto" %>
<%@ page import="it.mondovespapx.model.Categoria" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestione prodotti - MondoVespaPX</title>
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
    <h2>Gestione prodotti</h2>
    <h3>Inserisci nuovo prodotto</h3>
    <form action="${pageContext.request.contextPath}/admin/prodotti" method="post" enctype="multipart/form-data">
        
        <input type="hidden" name="azione" value="inserisci">

        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>

        <label for="descrizione">Descrizione:</label>
        <textarea id="descrizione" name="descrizione"></textarea>

        <label for="prezzo">Prezzo:</label>
        <input type="number" id="prezzo" name="prezzo" step="0.01" required>

        <label for="quantita">Quantità:</label>
        <input type="number" id="quantita" name="quantita" required>

        <label for="immagine">Immagine:</label>
        <input type="file" name="immagine" accept="image/*">

        <label for="idCategoria">Categoria:</label>
        <select id="idCategoria" name="idCategoria">
            <%
                //Recupera la lista delle categorie passata dalla Servlet tramite request.setAttribute
                List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
                
                //Genera dinamicamente le opzioni del menu a tendina ciclando le categorie del DB
                for (Categoria c : categorie) {
            %>
                <option value="<%= c.getId() %>"><%= c.getNome() %></option>
            <%
                }
            %>
        </select>

        <button type="submit">Inserisci</button>
    </form>
<h3>Prodotti nel catalogo</h3>
<%
    List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("prodotti");
    List<Categoria> categorieModifica = (List<Categoria>) request.getAttribute("categorie");
    for (Prodotto p : prodotti) {
%>
    <div class="prodotto">
        <p><strong><%= p.getNome() %></strong> — <%= p.getPrezzo() %> €</p>
        <form action="${pageContext.request.contextPath}/admin/prodotti" method="post" enctype="multipart/form-data">
            <input type="hidden" name="azione" value="modifica">
            <input type="hidden" name="id" value="<%= p.getId() %>">
            <label>Nome:</label>
            <input type="text" name="nome" value="<%= p.getNome() %>" required>
            <label>Descrizione:</label>
            <textarea name="descrizione"><%= p.getDescrizione() != null ? p.getDescrizione() : "" %></textarea>
            <label>Prezzo:</label>
            <input type="number" name="prezzo" step="0.01" value="<%= p.getPrezzo() %>" required>
            <label>Quantità:</label>
            <input type="number" name="quantita" value="<%= p.getQuantitaDisponibile() %>" required>
            <label>Nuova immagine (lascia vuoto per mantenere quella attuale):</label>
            <input type="file" name="immagine" accept="image/*">
            <label>Categoria:</label>
            <select name="idCategoria">
                <%
   					 for (Categoria c : categorieModifica) {
				%>
                    <option value="<%= c.getId() %>"
                        <%= c.getId() == p.getIdCategoria() ? "selected" : "" %>>
                        <%= c.getNome() %>
                    </option>
                <%
                    }
                %>
            </select>
            <button type="submit">Salva modifiche</button>
        </form>
        <form action="${pageContext.request.contextPath}/admin/prodotti" method="post">
            <input type="hidden" name="azione" value="elimina">
            <input type="hidden" name="id" value="<%= p.getId() %>">
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