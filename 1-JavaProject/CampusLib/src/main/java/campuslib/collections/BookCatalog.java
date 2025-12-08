package campuslib.collections;

import campuslib.models.Author;
import campuslib.models.Book;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @brief Il catalogo dei libri della biblioteca.
 */
public class BookCatalog {
    private HashMap<String, Book> catalog;

    /**
     * @brief Costruttore.
     * @post
     * Il catalogo libri è vuoto.
     */
    public BookCatalog() {
    }

    /**
     * @brief Aggiunge un libro al catalogo.
     * @param[in] book Libro da aggiungere.
     * @return Esito aggiunta.
     * @post
     * Il libro è aggiunto se rispetta il formato per ISBN, altrimenti restituisce false.
     */
    public boolean addBook(Book book) {
        return false;
    }

    /**
     * @brief Rimuove un libro dal catalogo.
     * @param[in] isbn ISBN del libro da rimuovere.
     * @return Esito rimozione.
     * @post
     * Il libro viene rimosso se presente nel catalogo, altrimenti restituisce false.
     */
    public boolean removeBook(String isbn) {
        return false;
    }

    /**
     * @brief Cerca libri nel catalogo per titolo.
     * @param[in] title Titolo del libro da cercare.
     * @return Lista di libri trovati (vuota se nessun libro corrisponde al criterio di ricerca).
     */
    public LinkedList<Book> searchByTitle(String title) {
        return null;
    }

    /**
     * @brief Cerca libri nel catalogo per autore.
     * @param[in] author Autore del libro da cercare.
     * @return Lista di libri trovati (vuota se nessun libro corrisponde al criterio di ricerca).
     */
    public LinkedList<Book> searchByAuthor(Author author) {
        return null;
    }

    /**
     * @brief Cerca un libro nel catalogo per ISBN.
     * @param[in] isbn Codice ISBN del libro da cercare.
     * @return Libro trovato o null se non presente.
     */
    public Book searchByIsbn(String isbn) {
        return null;
    }

    /**
     * @brief Cerca libri secondo un parametro di ricerca generico.
     * La ricerca viene effettuata su tutti gli attributi del libro:
     * titolo, autori (nome e cognome), ISBN e anno di pubblicazione.
     * @param[in] query Parametro di ricerca generico (da confrontare con gli attributi del libro).
     * @return Lista di libri che corrispondono alla query (vuota se nessun libro corrisponde al criterio di ricerca).
     */
    public LinkedList<Book> search(String query) {
        return null;
    }

    /**
     * @brief Restituisce una rappresentazione testuale del catalogo.
     * @return Rappresentazione testuale del catalogo.
     */
    public String toString() {
        return null;
    }

    /**
     * @brief Esporta il catalogo su file.
     * @param[in] fileName Nome del file da scrivere.
     * @post
     * Il catalogo viene salvato sul file specificato.
     */
    public void exportOnFile(String fileName) {
    }

    /**
     * @brief Importa un catalogo da file.
     * @param[in] fileName Nome del file da cui importare.
     * @return Catalogo importato o null se il file non è valido.
     */
    public static BookCatalog importFromFile(String fileName) {
        return null;
    }
}