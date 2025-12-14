package it.campuslib.domain.transactions;

import java.time.LocalDate;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.users.User;

/**
 * @brief Rappresenta la restituzione di un libro.
 * Estende Transaction registrando la data effettiva di restituzione del libro.
 * @see Transaction
 */
public class Giveback extends Transaction {
    private final LocalDate endDate;
    
    /**
     * @brief Istanzia un nuovo oggetto Giveback.
     * Il metodo istanzia un nuovo oggetto Giveback, utilizzando il costruttore della superclasse Transaction.
     * @param[in] borrowedBook Il riferimento all'oggetto libro che è stato restituito.
     * @param[in] borrowerUser L'utente che ha effettuato la restituzione.
     * @param[in] startDate La data di inizio del prestito.
     * @param[in] endDate La data di restituzione del libro.
     * @see Transaction
     * @pre
     * Il prestito del richiedente è attivo.
     * @post
     * Il prestito corrispondente viene aggiunto alla lista delle restituzioni.
     */
    public Giveback(Book borrowedBook, User borrowerUser, LocalDate startDate, LocalDate endDate) {
        super(borrowedBook, borrowerUser, startDate);
        this.endDate = endDate;
    }

    /**
     * @brief Istanzia un nuovo oggetto Giveback preservando l'ID del prestito originale.
     * Utilizzato per convertire un Loan in Giveback mantenendo lo stesso ID.
     * @param[in] id L'ID del prestito originale da preservare.
     * @param[in] borrowedBook Il libro restituito.
     * @param[in] borrowerUser L'utente che ha effettuato la restituzione.
     * @param[in] startDate La data di inizio del prestito.
     * @param[in] endDate La data di restituzione del libro.
     * @see Transaction
     */
    public Giveback(int id, Book borrowedBook, User borrowerUser, LocalDate startDate, LocalDate endDate) {
        super(id, borrowedBook, borrowerUser, startDate);
        this.endDate = endDate;
    }
    
    /**
     * @brief Restituisce l'effettiva data di restituzione del libro.
     * @return La data di restituzione del libro.
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * @brief Restituisce una rappresentazione testuale dell'oggetto Giveback.
     * @return Una stringa contenente le informazioni relative alla restituzione.
     */
    // FIXME: Implementare toString
    @Override
    public String toString() {
        return null;
    }
}