package it.campuslib.domain.catalog;

import java.io.Serializable;
import java.util.LinkedList;

import it.campuslib.collections.LoanRegistry;
import it.campuslib.domain.transactions.Loan;

/**
 * @brief Rappresenta un Libro.
 */
public class Book implements Comparable<Book>, Serializable {
    private final String isbn;
    private String title;
    private String authors;
    private int publishingYear;
    private AdoptionStatus status;
    private int copies;
    
    /**
     * @brief Istanzia un nuovo oggetto Book.
     * La funzione istanzia un nuovo oggetto Book con i parametri forniti,
     * implementando un controllo sul formato dell'ISBN.
     * @param[in] isbn Il codice ISBN del libro.
     * @param[in] title Il titolo del libro.
     * @param[in] authors Gli autori del libro come stringa.
     * @param[in] publishingYear L'anno di pubblicazione del libro.
     * @param[in] copies Il numero totale di copie del libro.
     * @see checkIsbn(String isbn)
     */
    public Book(String isbn, String title, String authors, int publishingYear, int copies) throws InvalidBookInfoException {
        if(!checkIsbn(isbn)) {
            throw new InvalidBookInfoException("Isbn non valido: " + isbn);
        }
   
        this.isbn = isbn;
        this.title = title;
        this.authors = authors != null ? authors : "";
        this.publishingYear = publishingYear;
        this.copies = copies;
        this.status = AdoptionStatus.ADOPTED;
    }
    
    /**
     * @brief Restituisce il codice ISBN del libro.
     * @return Il codice ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * @brief Restituisce il titolo del libro.
     * @return Il titolo del libro.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @brief Restituisce la stringa degli autori del libro.
     * @return La stringa degli autori del libro.
     */ 
    public String getAuthors() {
        return authors;
    }
    
    /**
     * @brief Restituisce l'anno di pubblicazione del libro.
     * @return L'anno di pubblicazione del libro.
     */
    public int getPublishingYear() {
        return publishingYear;
    }
        
    /**
     * @brief Restituisce il numero totale di copie del libro.
     * @return Il numero totale di copie del libro.
     */
    public int getCopies() {
        return copies;
    }
    
    /**
     * @brief Verifica se il libro è in adozione.
     * @return true se il libro è in adozione, false altrimenti.
     */
    public boolean isAdopted() {
        return this.status == AdoptionStatus.ADOPTED;
    }

    /**
     * @brief Restituisce lo stato di adozione del libro.
     * @return Lo stato di adozione.
     */
    public AdoptionStatus getStatus() {
        return this.status;
    }

    /**
     * @brief Restituisce una stringa essenziale per la visualizzazione in liste di selezione.
     * @return Stringa nel formato "Titolo - Autore".
     */
    public String getDisplayString() {
        return title + " - " + authors;
    }
    
    /**
     * @brief Verifica la disponibilità del libro.
     * Il metodo verifica la disponibilità del libro confrontando 
     * il numero di copie totali con il numero di prestiti associati al libro.
     * @return true se il libro è disponibile, false altrimenti.
     * @see getAssociatedLoans()
     */
    public boolean checkAvailability(LoanRegistry registry) {
        LinkedList<Loan> l = new LinkedList<>();
        l = registry.searchByBook(isbn);
        return (this.copies > l.size());
    }
    
    /**
     * @brief Imposta il titolo del libro.
     * @param[in] title Il nuovo titolo del libro.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @brief Imposta lo stato di adozione del libro.
     * @param[in] status Il nuovo stato di adozione del libro.
     */
    public void setStatus(AdoptionStatus status) {
        this.status = status;
    }

    /**
     * @brief Imposta l'anno di pubblicazione del libro.
     * @param[in] year Il nuovo anno di pubblicazione del libro.
     */
    public void setPublishingYear(int year) {
        this.publishingYear = year;
    }

    /**
     * @brief Imposta il numero di copie totali del libro.
     * @param[in] copies Il nuovo numero di copie totali del libro.
     */
    public void setCopies(int copies) {
        this.copies = copies;
    }

    /**
     * @brief Aggiunge un autore alla stringa degli autori del libro.
     * @param[in] author L'autore da aggiungere.
     * @return true se l'autore è stato aggiunto con successo, false altrimenti.
     */
    public boolean addAuthor(String author) {
        if (author != null && !author.trim().isEmpty() && !authors.contains(author)) {
            if (authors.isEmpty()) {
                this.authors = author;
            } else {
                this.authors += ", " + author;
            }
            return true;
        }
        return false;
    }
    
    /**
     * @brief Verifica se il formato dell'ISBN fornito è valido.
     * @param[in] isbn L'ISBN da controllare.
     * @return true se l'ISBN è valido, false altrimenti.
     * @see Book(String isbn, String title, String authors, int publishingYear, int copies)
     */
    private static boolean checkIsbn(String isbn) {
        
        
        if (isbn == null) {
            return false;
        }
        
        //Rimuove spazi bianchi iniziali e finali
        isbn=isbn.trim();
        if(isbn.isEmpty()) {
            return false;
        }
        
        if (isbn.length() != 13) {
            return false;
        } else {
            for (int i = 0; i < isbn.length(); i++) {
                if (!Character.isDigit(isbn.charAt(i))) {
                    return false;
                }
            }
            return (isbn.startsWith("978") || isbn.startsWith("979"));
        }
    } 

    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto Book.
     * @return Una stringa contenente le informazioni relative al libro.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        sb.append("\n");
        sb.append("Isbn: " + isbn);
        sb.append(" Titolo: " + title);
        sb.append(" Autori: " + authors);
        sb.append(" Anno di Pubblicazione: " + publishingYear);
        sb.append(" Stato: " + status);
        sb.append(" Copie totali: " + copies);
        sb.append("\n");
    
        return sb.toString();
    }

    /**
     * @brief Confronta l'oggetto Book corrente con un altro oggetto per verificarne l'uguaglianza.
     * @param[in] obj L'oggetto da confrontare con il libro corrente.
     * @return true se gli oggetti sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return this.isbn.equals(book.isbn);
    }

    /**
     * @brief Confronta l'oggetto Book corrente con un altro oggetto Book per l'ordinamento.
     * @param[in] other L'oggetto Book da confrontare con il libro corrente.
     * @return Un valore negativo, zero o positivo se il libro corrente
     * è rispettivamente minore, uguale o maggiore dell'altro libro.
     */
    @Override
    public int compareTo(Book other) {
        return this.isbn.compareTo(other.isbn);
    }

    /**
     * @brief Restituisce il codice hash dell'oggetto Book.
     * @return Il codice hash calcolato in base all'ISBN del libro.
     */
    @Override
    public int hashCode() {
        return this.isbn.hashCode();
    }
}