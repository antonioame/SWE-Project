package it.campuslib.domain.catalog;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @brief Rappresenta un Libro.
 */
public class Book implements Comparable<Book>, Serializable {
    private final String isbn;
    private String title;
    private ArrayList<Author> authors;
    private int publishingYear;
    private AdoptionStatus status;
    private int copies;
    
    /**
     * @brief Istanzia un nuovo oggetto Book.
     * La funzione istanzia un nuovo oggetto Book con i parametri forniti,
     * implementando un controllo sul formato dell'ISBN.
     * @param[in] isbn Il codice ISBN del libro.
     * @param[in] title Il titolo del libro.
     * @param[in] authors La lista degli autori del libro.
     * @param[in] publishingYear L'anno di pubblicazione del libro.
     * @param[in] copies Il numero totale di copie del libro.
     * @return Un nuovo oggetto Book.
     * @see checkISBN(String isbn)
     */
    public Book(String isbn, String title, ArrayList<Author> authors, int publishingYear, int copies)/*throws IllegalArgumentException*/ {
        /*if(!checkIsbn(isbn)){
            throw new IllegalArgumentException("Isbn non valido: " + isbn);
        }*/
        //gestire in loco anche il controllo formato sull'anno di pubblicazione o creare un metodo a parte? 
        //Considerando il secondo caso:
        /*if(!checkPublYear(publishingYear)){
            throw new IllegalArgumentException("Anno di pubblicazione non valido: " + publishingYear);
        }*/
        
        this.isbn = isbn;
        this.title = title;
        if(authors == null){
            this.authors = new ArrayList<>();
        }else{
            this.authors = new ArrayList<>(authors);
        }
        this.publishingYear = publishingYear;
        this.copies = copies;
        this.status = AdoptionStatus.ADOPTED;
        
      
    }
    
    // Metodi Getter
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
     * @brief Restituisce la lista degli autori del libro.
     * @return La lista degli autori del libro.
     */ 
    public ArrayList<Author> getAuthors() {
    
        return authors; 
        //oppure, per prevenire modifiche esterne:
        // return new ArrayList<>(authors);
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
     * @brief Verifica la disponibilità del libro.
     * Il metodo verifica la disponibilità del libro confrontando 
     * il numero di copie totali con il numero di prestiti associati al libro.
     * @return true se il libro è disponibile, false altrimenti.
     * @see getAssociatedLoans()
     */
    public boolean checkAvailability() {
    
        //private LinkedList<Loan> getAssociatedLoans();??

        return false;
    }
    
    
    
    // Metodi Setter
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

    // Altri metodi
    /**
     * @brief Aggiunge un autore alla lista degli autori del libro.
     * @param[in] author L'autore da aggiungere.
     * @return true se l'autore è stato aggiunto con successo, false altrimenti. 
     */
    public boolean addAuthor(Author author) {
     if (author != null && !authors.contains(author)) {
            return authors.add(author);
        }
        return false;
    }
    
    /**
     * @brief Verifica se il formato dell'ISBN fornito è valido.
     * @param[in] isbn L'Isbn da controllare.
     * @return true se l'Isbn è valido, false altrimenti.
     * @see Book(String isbn, String title, ArrayList<Author> authors, int publishingYear, int copies)
     */
    private boolean checkIsbn(String isbn) {
        if(isbn == null){
        return false;
        }    
        if(isbn.length()!=13){
         return false;
        }else{
            for(int i=0; i<isbn.length(); i++){
                if(!Character.isDigit(isbn.charAt(i))){
                    return false;
                }
            }
        return true;
        }
    } 
    /*
    private boolean checkPublYear(int publishingYear){
        if(publishingYear < 1000 || publishingYear > 2025){  //Nota, sto vincolando il software ad un aggiornamento annuale del codice..come risolvo? Tolgo il maggiore?
            return false;
        }else{
            return true;
        }
    }
    */
    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto Book.
     * @return Una stringa contenente le informazioni relative al libro.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer();
        
        sb.append("\n");
        sb.append("Isbn: "+isbn);
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