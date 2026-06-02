<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrazione - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
    <h1>MondoVespaPX</h1>
    <nav>
        <a href="${pageContext.request.contextPath}/catalogo">Catalogo</a>
        <a href="${pageContext.request.contextPath}/carrello">Carrello</a>
    </nav>
</header>
<main>
    <h2>Registrati</h2>
    <%--controlla se la Servlet ha riscontrato un problema--%>
    <% if (request.getAttribute("errore") != null) { %>
        <p class="errore"><%= request.getAttribute("errore") %></p>
    <% } %>
    <form action="${pageContext.request.contextPath}/registrazione" method="post">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" required>
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
        <button type="submit">Registrati</button>
    </form>
    <p>Hai già un account? <a href="${pageContext.request.contextPath}/login">Accedi</a></p>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>
</body>
</html>