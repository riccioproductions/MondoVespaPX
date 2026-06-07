package it.mondovespapx.control;

import it.mondovespapx.dao.CategoriaDAO;
import it.mondovespapx.dao.ProdottoDAO;
import it.mondovespapx.model.Categoria;
import it.mondovespapx.model.Prodotto;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

//Annotazione che permette il caricamento di file per le immagini dei prodotti
@MultipartConfig
@WebServlet("/admin/prodotti")
public class AdminProdottoServlet extends HttpServlet {

    //Mostra la pagina di gestione dei prodotti
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        try {
            List<Prodotto> prodotti = prodottoDAO.getAllProdotti();
            List<Categoria> categorie = categoriaDAO.getAllCategorie();           
            request.setAttribute("prodotti", prodotti);
            request.setAttribute("categorie", categorie);
            
        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }

        //Passa la gestione alla jsp
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/admin/prodotti.jsp");
        rd.forward(request, response);
    }
    //Riceve i dati inviati dai form
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Legge l'azione nel form per capire cosa fare
        String azione = request.getParameter("azione");
        ProdottoDAO dao = new ProdottoDAO();

        try {
            //Aggiunta di un prodotto
            if ("inserisci".equals(azione)) {

                String nomeFile = null;
                //Prende l'immagine caricata nel form
                Part filePart = request.getPart("immagine");

                //Controlla se l'utente ha effettivamente caricato un file
                if (filePart != null && filePart.getSize() > 0) {
                    //Prende il nome del file
                    nomeFile = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();               
                    //Trova la cartella "images" sul server
                    String uploadPath = getServletContext().getRealPath("/images/");
                    File uploadDir = new File(uploadPath);        
                    //Se la cartella non esiste, la crea
                    if (!uploadDir.exists()) uploadDir.mkdirs();
                    //Copia il file dal computer dell'utente alla cartella del server
                    InputStream input = filePart.getInputStream();
                    Files.copy(input, new File(uploadDir, nomeFile).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                //Crea un nuovo prodotto e ci mette dentro i dati scritti nel form
                Prodotto p = new Prodotto();
                p.setNome(request.getParameter("nome"));
                p.setDescrizione(request.getParameter("descrizione"));
                p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
                p.setQuantitaDisponibile(Integer.parseInt(request.getParameter("quantita")));     
                //Salva il nome dell'immagine e la categoria
                p.setImmagine(nomeFile);
                p.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));      
                //Salva il nuovo prodotto nel database
                dao.inserisci(p);

            //Modifica di un prodotto
            } else if ("modifica".equals(azione)) {
                Prodotto p = new Prodotto(); 
                
                // Prende l'ID del prodotto da cambiare e aggiorna tutti i suoi dati
                int idProdotto = Integer.parseInt(request.getParameter("id"));
                p.setId(idProdotto);
                p.setNome(request.getParameter("nome"));
                p.setDescrizione(request.getParameter("descrizione"));
                p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
                p.setQuantitaDisponibile(Integer.parseInt(request.getParameter("quantita")));           
                String idCategoriaParam = request.getParameter("idCategoria");
                if (idCategoriaParam != null && !idCategoriaParam.isEmpty()) {
                    p.setIdCategoria(Integer.parseInt(idCategoriaParam));
                }
                //Modifica dell'immagine
                Part filePart = request.getPart("immagine");
                if (filePart != null && filePart.getSize() > 0) {
                    // L'utente ha caricato un nuovo file: lo salvo e aggiorno il nome
                    String nomeFile = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String uploadPath = getServletContext().getRealPath("/images/");
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) uploadDir.mkdirs();
                    InputStream input = filePart.getInputStream();
                    Files.copy(input, new File(uploadDir, nomeFile).toPath(), StandardCopyOption.REPLACE_EXISTING);                    
                    p.setImmagine(nomeFile);
                } else {
                    //Se l'utente non ha caricato un file. l'immagine originale viene recuperata direttamente dal DB.
                    Prodotto prodottoOriginale = dao.getProdottoById(idProdotto);
                    if (prodottoOriginale != null) {
                        p.setImmagine(prodottoOriginale.getImmagine());
                    }
                }
                //Aggiorna i dati nel database
                dao.aggiorna(p);

            //Cancellazione di un prodotto
            } else if ("elimina".equals(azione)) {
                //Prende l'ID e lo cancella dal database
                int id = Integer.parseInt(request.getParameter("id"));
                dao.elimina(id);
            }

        } catch (SQLException e) {
            throw new ServletException("Errore database", e);
        }
        //Ricarica la pagina dei prodotti per mostrare i cambiamenti
        response.sendRedirect(request.getContextPath() + "/admin/prodotti");
    }
}