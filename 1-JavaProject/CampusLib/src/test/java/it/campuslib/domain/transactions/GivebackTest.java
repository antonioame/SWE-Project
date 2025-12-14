package it.campuslib.domain.transactions;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.campuslib.domain.catalog.Author;
import it.campuslib.domain.catalog.Book;
import it.campuslib.domain.users.User;
import it.campuslib.domain.users.InvalidUserInfoException;

public class GivebackTest {

    private Book borrowedBook;
    private User borrowerUser;
    private LocalDate startDate;
    private ArrayList<Author> authors;

    public GivebackTest() {
    }

    @BeforeEach
    public void setUp() throws InvalidUserInfoException{
        authors = new ArrayList<>();
        authors.add(new Author("Claudio", "De Sio Cesari"));
        borrowedBook = new Book("9788836018123", "Programmazione Java", authors, 2025, 1);
        borrowerUser = new User("Giovanni", "Rossi", "0612709852", "g.rossi36@studenti.unisa.it");
        startDate = LocalDate.of(2025, 12, 1);

    }

    @Test
    public void testGiveback() {
    }

    @Test
    public void testConstructor() {
        Giveback giveback = new Giveback(borrowedBook, borrowerUser, startDate, LocalDate.now());
        assertNotNull(giveback);
        assertEquals(borrowedBook, giveback.getBorrowedBook());
        assertEquals(borrowerUser, giveback.getBorrowerUser());
        assertEquals(startDate, giveback.getStartDate());
        assertEquals(LocalDate.now(), giveback.getEndDate());
    }

    @Test
    public void testConstructorWithId() {
        int testId = 42;
        LocalDate endDate = LocalDate.of(2025, 12, 15);
        Giveback giveback = new Giveback(testId, borrowedBook, borrowerUser, startDate, endDate);
        assertNotNull(giveback);
        assertEquals(testId, giveback.getId());
        assertEquals(borrowedBook, giveback.getBorrowedBook());
        assertEquals(borrowerUser, giveback.getBorrowerUser());
        assertEquals(startDate, giveback.getStartDate());
        assertEquals(endDate, giveback.getEndDate());
    }

    @Test
    public void testEqualsDifferentId() {
        /*Due Giveback con ID diversi non sono uguali */
        Giveback g1 = new Giveback(borrowedBook, borrowerUser, startDate, LocalDate.now());
        Giveback g2 = new Giveback(borrowedBook, borrowerUser, startDate, LocalDate.now().plusDays(1));
        assertFalse(g1.equals(g2));
    }

    @Test
    public void testEqualsSameId() {
        /*Due Giveback con stesso ID sono uguali */
        int sameId = 200;
        LocalDate endDate = LocalDate.of(2025, 12, 15);
        Giveback g1 = new Giveback(sameId, borrowedBook, borrowerUser, startDate, endDate);
        Giveback g2 = new Giveback(sameId, borrowedBook, borrowerUser, startDate, endDate);
        assertTrue(g1.equals(g2));
    }

    @Test
    public void testHashCode() {
        /*HashCode dovrebbe essere consistente e uguale per oggetti uguali */
        int sameId = 201;
        LocalDate endDate = LocalDate.of(2025, 12, 15);
        Giveback g1 = new Giveback(sameId, borrowedBook, borrowerUser, startDate, endDate);
        Giveback g2 = new Giveback(sameId, borrowedBook, borrowerUser, startDate, endDate);
        
        // Oggetti uguali hanno stesso hashCode
        assertEquals(g1.hashCode(), g2.hashCode());
        
        // HashCode consistente per lo stesso oggetto
        int hash1 = g1.hashCode();
        int hash2 = g1.hashCode();
        assertEquals(hash1, hash2);
    }

}
