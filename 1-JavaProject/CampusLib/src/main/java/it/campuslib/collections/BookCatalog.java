package it.campuslib.collections;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.catalog.AdoptionStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @brief Il catalogo dei libri della biblioteca.
 */
public class BookCatalog implements Serializable{
    private HashMap<String, Book> catalog;
    private transient ObservableList<Book> books;

    /**
     * @brief Costruttore.
     * @post
     * Il catalogo libri è vuoto.
     */
    public BookCatalog() {
        this.catalog = new HashMap<>();
        this.books = FXCollections.observableArrayList();
    }

    /**
     * @brief Aggiunge un libro al catalogo.
     * @param[in] book Libro da aggiungere.
     * @return Esito aggiunta.
     * @post
     * Il libro è aggiunto se rispetta il formato per ISBN, altrimenti restituisce false.
     */
    public boolean addBook(Book book) {
        if (book == null || book.getIsbn() == null) {
            return false;
        }
        
        String isbn = book.getIsbn();
        // Non sovrascrivere il libro se già esiste uno con stesso ISBN
        if (catalog.containsKey(isbn)) {
            return false;
        }

        catalog.put(isbn, book);
        books.add(book);
        return true;
    }

    /**
     * @brief Rimuove un libro dal catalogo.
     * @param[in] isbn ISBN del libro da rimuovere.
     * @return Esito rimozione.
     * @post
     * Il libro viene rimosso se presente nel catalogo, altrimenti restituisce false.
     */
    public boolean removeBook(String isbn) {
        if (isbn == null) {
            return false;
        }
        Book bookToRemove = catalog.get(isbn);
        
        /*Precondizione da aggiungere: Libro registrato
        if (bookToRemove == null) {  //libro non trovato
            return false;
        }
        */
        
        //Aggiornamento stato libro
        bookToRemove.setStatus(AdoptionStatus.NOT_ADOPTED);
        return true;
    }

    /**
     * @brief Cerca libri nel catalogo per titolo.
     * @param[in] title Titolo del libro da cercare.
     * @return Lista di libri trovati (vuota se nessun libro corrisponde al criterio di ricerca).
     */
    public LinkedList<Book> searchByTitle(String title) {
        //Se il titolo è vuoto o contiene solo spazi restituisce una lista vuota
        if (title == null || title.trim().isEmpty()) {
            return new LinkedList<>();
        }
        final String searchTitle = title.trim().replaceAll("\\s+", " ").toLowerCase();
        return catalog.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTitle))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @brief Cerca libri nel catalogo per autore.
     * @param[in] authorName Nome dell'autore del libro da cercare.
     * @return Lista di libri trovati (vuota se nessun libro corrisponde al criterio di ricerca).
     */
    public LinkedList<Book> searchByAuthor(String authorName) {
        if (authorName == null || authorName.trim().isEmpty()) {
            return new LinkedList<>();
        }
        
        return catalog.values().stream()
                .filter(book -> book.getAuthors().contains(authorName))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @brief Cerca un libro nel catalogo per ISBN.
     * @param[in] isbn Codice ISBN del libro da cercare.
     * @return Libro trovato o null se non presente.
     */
    public Book searchByIsbn(String isbn) {
        if (isbn == null) {
            return null;
        }
        return catalog.get(isbn);
    }

    /**
     * @brief Cerca libri secondo un parametro di ricerca generico.
     * La ricerca viene effettuata su tutti gli attributi del libro:
     * titolo, autori (nome e cognome), ISBN e anno di pubblicazione.
     * @param[in] query Parametro di ricerca generico (da confrontare con gli attributi del libro).
     * @return Lista di libri che corrispondono alla query (vuota se nessun libro corrisponde al criterio di ricerca).
     */
    public LinkedList<Book> search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new LinkedList<>();
        }

        final String searchQuery = query.toLowerCase();
        
        return catalog.values().stream()
                .filter(book -> {
                    // Criterio 1: Titolo
                    if (book.getTitle().toLowerCase().contains(searchQuery)) return true;

                    // Criterio 2: ISBN (lo confrontiamo come stringa)
                    if (book.getIsbn().contains(searchQuery)) return true;

                    // Criterio 3: Anno di pubblicazione
                    if (String.valueOf(book.getPublishingYear()).contains(searchQuery)) return true;

                    // Criterio 4: Autori
                    return book.getAuthors().toLowerCase().contains(searchQuery);
                }).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @brief Restituisce una rappresentazione testuale del catalogo.
     * @return Rappresentazione testuale del catalogo.
     */
    public String toString() {
        if (catalog.isEmpty()) {
            return "Il catalogo è attualmente vuoto.\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n Catalogo Libri (Totale: ").append(catalog.size()).append(" libri distinti) \n");
        
        for (Book book : catalog.values()) {
            sb.append(book.toString());
        }
        sb.append("\n");
        
        return sb.toString();
    }

    /**
     * @brief Esporta il catalogo su file.
     * @param[in] fileName Nome del file da scrivere.
     * @post
     * Il catalogo viene salvato sul file specificato.
     * In caso di errore, il metodo termina senza sollevare eccezioni.
     */
    public void exportOnFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            // System.err.println("Errore: Il nome del file non può essere vuoto.");
            return;
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(catalog);
            // System.out.println("Catalogo esportato con successo in " + fileName);
        } catch (IOException e) {
            // In caso di errore, il metodo termina senza sollevare eccezioni
            System.err.println("ERR. Esportazione Non Riuscita: " + e.getMessage());
        }
    }

    /**
     * @brief Importa un catalogo da file.
     * @param[in] fileName Nome del file da cui importare.
     * @return Il catalogo importato se il file è valido, null altrimenti.
     */
    public static BookCatalog importFromFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            // Notazione per sopprimere l'avviso del compilatore per il cast non controllato dell'oggetto da Object Input Stream a Set<Loan>
            @SuppressWarnings("unchecked")
            
            HashMap<String, Book> loadedCatalog = (HashMap<String, Book>) ois.readObject();
            BookCatalog newCatalog = new BookCatalog();
            newCatalog.catalog = loadedCatalog;
            newCatalog.books.addAll(loadedCatalog.values());
            instance = newCatalog;
            return newCatalog;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * @brief Restituisce il numero di libri presenti nel catalogo.
     * @return Numero di libri presenti nel catalogo.
     */
    public int getCatalogSize() {
        return this.catalog.size();
    }

    /**
     * @brief Restituisce tutti i libri del catalogo.
     * @return Lista di tutti i libri.
     */
    public ObservableList<Book> getAllBooks() {
        return books;
    }

    private static BookCatalog instance = null;

    public static BookCatalog getInstance() {
        if (instance == null) {
            instance = importFromFile("personal-files/io-binary-files/books.dat");
            if (instance == null) {
                instance = new BookCatalog();
            }
        }
        return instance;
    }
}