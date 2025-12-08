package campuslib.models;

import java.time.LocalDate;

/**
 * @brief Rappresenta la restituzione di un libro.
 * Estende Transaction registrando la data effettiva di restituzione del libro.
 * @see Transaction
 */
public class Giveback extends Transaction {
    private final LocalDate endDate;
    
    /**
     * @brief Instanzia un nuovo oggetto GiveBack.
     * Il metodo istanzia un nuovo oggetto GiveBack, utilizzando il costruttore della superclasse Transaction.
     * @param[in] borrowedBook Il riferimento all'oggetto libro che è stato restituito.
     * @param[in] borrowerUser L'utente che ha effettuato la restituzione.
     * @param[in] startDate La data di inizio del prestito.
     * @param[in] endDate La data di restituzione del libro.
     * @return Un nuovo oggetto GiveBack.
     * @see Transaction
     * @pre
     * Il prestito del richiedente è attivo.
     * @post
     * Il prestito corrispondente viene aggiunto alla lista delle restituzioni.
     */
    public GiveBack(Book borrowedBook, User borrowerUser, LocalDate startDate, LocalDate endDate) {
    }

    
    /**
     * @brief Restituisce l'effettiva data di restituzione del libro.
     * @return La data di restituzione del libro.
     */
    public LocalDate getEndDate() {
    
        return null;
    }

    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto GiveBack.
     * @return Una stringa contenente le informazioni relative alla restituzione.
     */
    public String toString() {
    
        return null;
    }
}