package it.campuslib.domain.catalog;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.campuslib.collections.LoanRegistry;
import it.campuslib.domain.transactions.InvalidLoanInfoException;
import it.campuslib.domain.transactions.Loan;
import it.campuslib.domain.users.InvalidUserInfoException;
import it.campuslib.domain.users.User;

/**
 *
 * @author Seren
 */
public class BookTest {
    private Author author1;
    private Author author2;
    private Book defaultBook1;
    private ArrayList<Author> defaultAuthors;
    private Book CloneB1;
    private Book defaultBook2;
    private Book defaultBook3;
    private Book CloneB2;
    
    //per testare la checkAvailability()
    private LoanRegistry testRegistry;
    private User testUser;
    
    @BeforeEach
    public void setUp() throws InvalidBookInfoException, InvalidUserInfoException {
       
        author1 = new Author("Poeta", "Omero");
        author2 = new Author("Daniel", "Defoe");
                
        defaultAuthors = new ArrayList<>();
        defaultAuthors.add(author1);
        defaultBook1 = new Book("9781234567890", "L'Odissea", defaultAuthors, 2025, 7);
        CloneB1 = new Book("9781234567890", "L'Odissea", defaultAuthors, 2025, 7);
        
        //checkAvailability
        testRegistry = new LoanRegistry();
        testUser = new User("Mario", "Rossi", "1112701345", "mario.rossi@studenti.unisa.it");
    }

    @Test
    public void testGetIsbn() {
        assertEquals("9781234567890", defaultBook1.getIsbn());
    }

    @Test
    public void testGetTitle() {
        assertEquals("L'Odissea", defaultBook1.getTitle());
    }

    @Test
    public void testGetAuthors() {
        ArrayList<Author> authors = defaultBook1.getAuthors();
        assertFalse(authors.isEmpty());           // La lista autori non dovrebbe essere vuota
        assertTrue(authors.contains(author1));    //  La lista autori deve contenere l'author1. [Se la get fallisce,controllare prima che la testAdd abbia avuto successo]
    }

    @Test
    public void testGetPublishingYear() {
        assertEquals(2025, defaultBook1.getPublishingYear());

    }

    @Test
    public void testGetCopies() {
        assertEquals(7, defaultBook1.getCopies());
    }

    @Test
    public void testIsAdopted() {
        assertTrue(defaultBook1.isAdopted());

    }

    @Test
    public void testCheckAvailability_Available() throws InvalidLoanInfoException {
        //7 copie e 0 prestiti
        assertTrue(defaultBook1.checkAvailability(testRegistry));
        
        //aggiungiamo 4 prestiti
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(1)));
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(2)));
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(3)));
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(4)));

        assertTrue(defaultBook1.checkAvailability(testRegistry));
        
        //aggiungiamo altri 4 prestiti
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(5)));
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(6)));
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(7)));
        testRegistry.addLoan(new Loan( defaultBook1, testUser, LocalDate.now(), LocalDate.now().plusMonths(8)));

        assertFalse(defaultBook1.checkAvailability(testRegistry));
        }
    
    
    @Test
    public void testSetTitle() {
        String newTitle = "Odissea Moderna";
        defaultBook1.setTitle(newTitle);
        assertEquals(newTitle, defaultBook1.getTitle());
    }

    @Test
    public void testSetStatus() {
        defaultBook1.setStatus(AdoptionStatus.NOT_ADOPTED);
        assertEquals(false, defaultBook1.isAdopted());
    }

    @Test
    public void testSetPublishingYear() {
        int newYear = 2022;
        defaultBook1.setPublishingYear(newYear);
        assertEquals(newYear, defaultBook1.getPublishingYear());
    }

    @Test
    public void testSetCopies() {
        int newCopies = 36;
        defaultBook1.setCopies(newCopies);
        assertEquals(newCopies, defaultBook1.getCopies());   
    }

    @Test
    public void testAddAuthor() {
        assertTrue(defaultBook1.addAuthor(author2));
        assertTrue(defaultBook1.getAuthors().contains(author2));
        assertEquals(2, defaultBook1.getAuthors().size());
    }

    @Test
    public void testToString() {
        String expectedAuthorString = "[Nome: Omero Cognome: Poeta]";
        String expected = new StringBuilder()
            .append("\n")
            .append("Isbn: 9781234567890")
            .append(" Titolo: L'Odissea")
            .append(" Autori: " + expectedAuthorString)
            .append(" Anno di Pubblicazione: 2025")
            .append(" Stato: ADOPTED") // Nota: l'enum viene stampato come stringa
            .append(" Copie totali: 7")
            .append("\n")
            .toString();
        assertEquals(expected, defaultBook1.toString());
    }

    @Test
    public void testEquals() throws InvalidBookInfoException {
        CloneB1.addAuthor(author2); 
        assertTrue(defaultBook1.equals(CloneB1));
        defaultBook2 = new Book("9781234597890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        assertFalse(defaultBook1.equals(defaultBook2));
    }

    @Test
    public void testCompareTo() throws InvalidBookInfoException {
        defaultBook2 = new Book("9781134497890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        CloneB2 = new Book("9781134497890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        defaultBook3 = new Book("9789234569890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        assertTrue(defaultBook1.compareTo(defaultBook2) > 0);
        assertTrue(defaultBook1.compareTo(defaultBook3) < 0);
        assertTrue(defaultBook2.compareTo(CloneB2) == 0);
    }
   
    @Test
    public void testHashCode() {
        assertEquals(defaultBook1.hashCode(), CloneB1.hashCode());
    }
    
    @Test
    public void testCheckIsbn_Success_ValidPrefix() throws InvalidBookInfoException {
        // 1. Prefisso 978, isbn valido, non deve lanciare eccezioni.
        assertDoesNotThrow(() -> {
            new Book("9781234567890", "Titolo", defaultAuthors, 2020, 1);
        }); 

        // 2. Prefisso 979, isbn valido, non deve lanciare eccezioni.
        assertDoesNotThrow(() -> {
            new Book("9791234567890", "Titolo", defaultAuthors, 2020, 1);
        });
    }
    
    @Test
    public void testCheckIsbn_Fail_NullOrEmpty() {
        // 1. ISBN Nullo, dovrebbe lanciare eccezione.
        assertThrows(InvalidBookInfoException.class, () -> {
            new Book(null, "Titolo", defaultAuthors, 2020, 1);
        });

        // 2. ISBN Vuoto, dovrebbe lanciare eccezione
        assertThrows(InvalidBookInfoException.class, () -> {
            new Book("", "Titolo", defaultAuthors, 2020, 1);
        });
    }
    
    @Test
    public void testCheckIsbn_Fail_InvalidLength() {
        // 1. Troppo Corto (9 caratteri), dovrebbe lanciare eccezione
        assertThrows(InvalidBookInfoException.class, () -> {
            new Book("978123456", "Titolo", defaultAuthors, 2020, 1);
        });

        // 2. Troppo Lungo (16 caratteri), dovrebbe lanciare eccezione
        assertThrows(InvalidBookInfoException.class, () -> {
            new Book("9781234567890861", "Titolo", defaultAuthors, 2020, 1);
        });
    }
    
    @Test
    public void testCheckIsbn_Fail_NonNumeric() {
        // Carattere non numerico, dovrebbe lanciare eccezione
        assertThrows(InvalidBookInfoException.class, () -> {
            new Book("978A234567890", "Titolo", defaultAuthors, 2020, 1);
        });
    }
    
    @Test
    public void testCheckIsbn_Fail_InvalidPrefix() {
        // Prefisso valido, dovrebbe lanciare eccezione
        assertThrows(InvalidBookInfoException.class, () -> {
            new Book("1231234567890", "Bad Prefix", defaultAuthors, 2020, 1);
        });
    }
}
