<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - MondoVespaPX</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <script src="${pageContext.request.contextPath}/scripts/validazione.js" defer></script>
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
    <h2>Accedi</h2>
	<!-- Verifica se la servlet di login ha generato un messaggio d'errore -->
    <% if (request.getAttribute("errore") != null) { %>
        <p class="errore"><%= request.getAttribute("errore") %></p>
    <% } %>
    <form id="formLogin" action="${pageContext.request.contextPath}/login" method="post">

        <label for="email">Email:</label>
        <input type="email" id="email" name="email">
        <span class="errore" id="erroreEmail"></span>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password">
        <span class="errore" id="errorePassword"></span>

        <button type="submit">Accedi</button>
    </form>
    <p>Non hai un account? <a href="${pageContext.request.contextPath}/registrazione">Registrati</a></p>
</main>
<footer>
    <p>MondoVespaPX - Ricambi Vespa PX</p>
</footer>

</body>
</html>