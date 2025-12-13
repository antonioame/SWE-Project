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
public abstract class Transaction implements Serializable {
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

    /*
     * FIXME: Implementare metodo equals basato su ID univoco della transazione
     * Nonché sulla verifica della classe dell'oggetto istanziato
     */
}
