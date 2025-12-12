package it.campuslib.domain.catalog;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

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
    
    @BeforeEach
    public void setUp() {
       
        author1 = new Author("Poeta", "Omero");
        author2 = new Author("Daniel", "Defoe");
                
        defaultAuthors = new ArrayList<>();
        defaultAuthors.add(author1);
        defaultBook1 = new Book("9781234567890", "L'Odissea", defaultAuthors, 2025, 7);
        CloneB1 = new Book("9781234567890", "L'Odissea", defaultAuthors, 2025, 7);
        
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
    public void testCheckAvailability() {

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
        String expectedAuthorString = "Nome: Omero Cognome: Poeta";
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
    public void testEquals() {
        CloneB1.addAuthor(author2); 
        assertTrue(defaultBook1.equals(CloneB1));
        defaultBook2 = new Book("9771234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        assertFalse(defaultBook1.equals(defaultBook2));
    }

    @Test
    public void testCompareTo() {
        defaultBook2 = new Book("9771234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        CloneB2 = new Book("9771234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        defaultBook3 = new Book("9871234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        assertTrue(defaultBook1.compareTo(defaultBook2)>0);
        assertTrue(defaultBook1.compareTo(defaultBook3)<0);
        assertTrue(defaultBook2.compareTo(CloneB2)==0);
    }
   
    @Test
    public void testHashCode() {
        assertEquals(defaultBook1.hashCode(), CloneB1.hashCode());
    }
    
}
