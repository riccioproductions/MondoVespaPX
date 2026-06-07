document.addEventListener("DOMContentLoaded", function () {
    /*Errori*/
    function mostraErrore(id, messaggio) {
        var el = document.getElementById(id);
        if (el) el.textContent = messaggio;
    }

    function rimuoviErrore(id) {
        var el = document.getElementById(id);
        if (el) el.textContent = "";
    }
	
    /*Regex*/
    var regexEmail = /^\S+@\S+\.\S+$/;
    var regexPassword = /^.{6,}$/;
    var regexNome = /^[A-Za-zÀ-ù]{2,}$/;
    var regexIndirizzo = /^\w+(\s\w+)+$/;

    /*Validazione login*/
    var formLogin = document.getElementById("formLogin");
    if (formLogin) {
        var emailLogin = document.getElementById("email");
        var passwordLogin = document.getElementById("password");
        emailLogin.addEventListener("change", function () {
            if (!regexEmail.test(this.value.trim())) {
                mostraErrore("erroreEmail", "Inserisci un'email valida");
            } else {
                rimuoviErrore("erroreEmail");
            }
        });

        passwordLogin.addEventListener("change", function () {
            if (!regexPassword.test(this.value)) {
                mostraErrore("errorePassword", "La password deve avere almeno 6 caratteri");
            } else {
                rimuoviErrore("errorePassword");
            }
        });

        formLogin.addEventListener("submit", function (e) {
            var valido = true;
            if (!regexEmail.test(emailLogin.value.trim())) {
                mostraErrore("erroreEmail", "Inserisci un'email valida");
                valido = false;
            } else {
                rimuoviErrore("erroreEmail");
            }
            if (!regexPassword.test(passwordLogin.value)) {
                mostraErrore("errorePassword", "La password deve avere almeno 6 caratteri");
                valido = false;
            } else {
                rimuoviErrore("errorePassword");
            }
            if (!valido) e.preventDefault();
        });
    }
    /*Registrazione*/
    var formRegistrazione = document.getElementById("formRegistrazione");
    if (formRegistrazione) {
        var nome = document.getElementById("nome");
        var cognome = document.getElementById("cognome");
        var emailReg = document.getElementById("email");
        var passwordReg = document.getElementById("password");
        nome.addEventListener("change", function () {
            if (!regexNome.test(this.value.trim())) {
                mostraErrore("erroreNome", "Inserisci un nome valido (solo lettere, min 2 caratteri)");
            } else {
                rimuoviErrore("erroreNome");
            }
        });
		
        cognome.addEventListener("change", function () {
            if (!regexNome.test(this.value.trim())) {
                mostraErrore("erroreCognome", "Inserisci un cognome valido (solo lettere, min 2 caratteri)");
            } else {
                rimuoviErrore("erroreCognome");
            }
        });

        emailReg.addEventListener("change", function () {
            if (!regexEmail.test(this.value.trim())) {
                mostraErrore("erroreEmail", "Inserisci un'email valida");
            } else {
                rimuoviErrore("erroreEmail");
            }
        });

        passwordReg.addEventListener("change", function () {
            if (!regexPassword.test(this.value)) {
                mostraErrore("errorePassword", "La password deve avere almeno 6 caratteri");
            } else {
                rimuoviErrore("errorePassword");
            }
        });

        formRegistrazione.addEventListener("submit", function (e) {
            var valido = true;

            if (!regexNome.test(nome.value.trim())) {
                mostraErrore("erroreNome", "Inserisci un nome valido");
                valido = false;
            } else {
                rimuoviErrore("erroreNome");
            }

            if (!regexNome.test(cognome.value.trim())) {
                mostraErrore("erroreCognome", "Inserisci un cognome valido");
                valido = false;
            } else {
                rimuoviErrore("erroreCognome");
            }

            if (!regexEmail.test(emailReg.value.trim())) {
                mostraErrore("erroreEmail", "Inserisci un'email valida");
                valido = false;
            } else {
                rimuoviErrore("erroreEmail");
            }
            if (!regexPassword.test(passwordReg.value)) {
                mostraErrore("errorePassword", "La password deve avere almeno 6 caratteri");
                valido = false;
            } else {
                rimuoviErrore("errorePassword");
            }
            if (!valido) e.preventDefault();
        });
    }

    /*Validazione Ordine*/
    var formOrdine = document.getElementById("formOrdine");
    if (formOrdine) {

        var indirizzo = document.getElementById("indirizzo");
        var metodo = document.getElementById("metodoPagamento");
        indirizzo.addEventListener("change", function () {
            if (!regexIndirizzo.test(this.value.trim())) {
                mostraErrore("erroreIndirizzo", "Inserisci un indirizzo valido (es. Via Roma 1)");
            } else {
                rimuoviErrore("erroreIndirizzo");
            }
        });
        formOrdine.addEventListener("submit", function (e) {
            var valido = true;

            if (!regexIndirizzo.test(indirizzo.value.trim())) {
                mostraErrore("erroreIndirizzo", "Inserisci un indirizzo valido");
                valido = false;
            } else {
                rimuoviErrore("erroreIndirizzo");
            }
            if (metodo.value === "") {
                mostraErrore("erroreMetodo", "Seleziona un metodo di pagamento");
                valido = false;
            } else {
                rimuoviErrore("erroreMetodo");
            }
            if (!valido) e.preventDefault();
        });
    }
	
	/* Verifica se email esiste */
	var emailReg2 = document.getElementById("email");
	if (formRegistrazione && emailReg2) {
	    emailReg2.addEventListener("blur", function () {
	        var email = this.value.trim();
	        var errore = document.getElementById("erroreEmail");
	        if (email === "" || !regexEmail.test(email)) {
	            return;
	        }
	        var xhr = new XMLHttpRequest();
	        xhr.open("GET", "verifica-email?email=" + encodeURIComponent(email), true);

	        xhr.onreadystatechange = function () {
	            if (xhr.readyState === 4 && xhr.status === 200) {
	                var risposta = JSON.parse(xhr.responseText);
	                if (risposta.esiste) {
	                    errore.textContent = "Questa email è già registrata";
	                } else {
	                    errore.textContent = "";
	                }
	            }
	        };
	        xhr.send(null);
	    });
	}
	
	/* Validazione profilo per cambio password */

	var formProfilo = document.getElementById("formProfilo");
	if (formProfilo) {
	    formProfilo.addEventListener("submit", function(e) {
	        var nuovaPassword = document.getElementById("nuovaPassword");
	        var confermaPassword = document.getElementById("confermaPassword");
	        var errore = document.getElementById("errorePassword");

	        if (nuovaPassword.value !== "" && nuovaPassword.value.length < 6) {
	            errore.textContent = "La password deve avere almeno 6 caratteri";
	            e.preventDefault();
	            return;
	        }

	        if (nuovaPassword.value !== confermaPassword.value) {
	            errore.textContent = "Le password non coincidono";
	            e.preventDefault();
	            return;
	        }

	        errore.textContent = "";
	    });
	}
});