package it.campuslib.domain.transactions;

import java.io.Serializable;
import java.time.LocalDate;

import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.users.User;

/**
 * @brief Classe astratta che rappresenta una transazione nella biblioteca.
 * Superclasse per Loan e Giveback.
 * @see Loan
 * @see Giveback
 */
public abstract class Transaction implements Serializable, Comparable<Transaction> {
    private static int nextId = 1;
    private final int id;
    private final Book borrowedBook;
    private final User borrowerUser;
    private final LocalDate startDate;

    /**
     * @brief Istanzia un nuovo oggetto Transaction.
     * @param[in] borrowedBook Il libro preso in prestito.
     * @param[in] borrowerUser L'utente che ha preso in prestito il libro.
     * @param[in] startDate La data di inizio del prestito.
     * @pre
     * - il libro deve trovarsi nello stato "ADOPTED".
     * - l'utente deve essere attivo.
     */
    public Transaction(Book borrowedBook, User borrowerUser, LocalDate startDate) {
        this.id = nextId++;
        this.borrowedBook = borrowedBook;
        this.borrowerUser = borrowerUser;
        this.startDate = startDate;
    }

    /**
     * @brief Istanzia un nuovo oggetto Transaction con un ID specifico.
     * Consente di preservare l'ID quando si converte un tipo (sottoclasse) di transazione in un altro.
     * @param[in] id L'ID da assegnare alla transazione.
     * @param[in] borrowedBook Il libro preso in prestito.
     * @param[in] borrowerUser L'utente che ha preso in prestito il libro.
     * @param[in] startDate La data di inizio del prestito.
     */
    protected Transaction(int id, Book borrowedBook, User borrowerUser, LocalDate startDate) {
        this.id = id;
        this.borrowedBook = borrowedBook;
        this.borrowerUser = borrowerUser;
        this.startDate = startDate;
    }

    /**
     * @brief Restituisce l'ID univoco del prestito.
     * @return L'ID univoco del prestito.
     */
    public int getId() {
        return this.id;
    }

    /**
     * @brief Restituisce il libro preso in prestito.
     * @return Il libro preso in prestito.
     */
    public Book getBorrowedBook() {
        return this.borrowedBook;
    }

    /**
     * @brief Restituisce l'utente che ha preso in prestito il libro.
     * @return L'utente che ha preso in prestito il libro.
     */
    public User getBorrowerUser() {
        return this.borrowerUser;
    }

    /**
     * @brief Restituisce la data di inizio del prestito.
     * @return La data di inizio del prestito.
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * @brief Restituisce la data utilizzata per l'ordinamento delle transazioni.
     * @return La data di ordinamento (expectedReturnDate per Loan, endDate per Giveback).
     */
    public abstract LocalDate getSortingDate();

    /**
     * @brief Confronta istanza di Transaction con un altro oggetto per uguaglianza.
     * Due oggetti Transaction sono considerati uguali se hanno lo stesso ID univoco e appartengono alla stessa classe.
     * @param[in] obj L'oggetto da confrontare.
     * @return true se gli oggetti sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (this.getClass() != obj.getClass()) return false;
        
        Transaction t = (Transaction) obj;
        return this.id == t.id;
    }

    /**
     * @brief Restituisce il codice hash dell'oggetto.
     * Il codice hash è basato sull'ID univoco della transazione.
     * @return Il codice hash dell'oggetto.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(this.id);
    }

    /**
     * @brief Confronta l'oggetto Transaction corrente con un altro oggetto Transaction per l'ordinamento.
     * L'ordinamento è basato prima sulla data di ordinamento (getSortingDate), poi sull'ID della transazione per evitare collisioni.
     * @param[in] other L'oggetto Transaction da confrontare con la transazione corrente.
     * @return Un valore negativo, zero o positivo se la transazione corrente è minore, uguale o maggiore dell'altra transazione.
     */
    @Override
    public int compareTo(Transaction other) {
        int dateCompare = this.getSortingDate().compareTo(other.getSortingDate());
        if (dateCompare != 0) {
            return dateCompare;
        } else {
            return Integer.compare(this.getId(), other.getId());
        }
    }

    // FIXME: Implementare toString
    @Override
    public String toString() {
    
        StringBuilder sb = new StringBuilder();
        
        sb.append("ID: ").append(id).append("\n");
        sb.append("Libro preso in prestito: ").append(borrowedBook).append("\n");
        sb.append("Utente che ha effettuato il prestito: ").append(borrowerUser).append("\n");
        sb.append("Data di registrazione del prestito: ").append(startDate).append("\n");
        
        return sb.toString();
    }
}
