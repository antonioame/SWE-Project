package it.campuslib.domain.catalog;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
// import it.campuslib.domain.catalog.Book;

/**
 *
 * @author Seren
 */
public class BookTest {
    private Author author1;
    private Author author2;
    private Book defaultBook;
    private ArrayList<Author> defaultAuthors;
    private Book CloneB1;
    private Book CloneB2;
    
    @BeforeEach
    public void setUp() {
       
        author1 = new Author("Poeta", "Omero");
        author2 = new Author("Daniel", "Defoe");
                
        defaultAuthors = new ArrayList<>();
        defaultAuthors.add(author1);
        defaultBook = new Book("9781234567890", "L'Odissea", defaultAuthors, 2024, 5);
        CloneB1 = new Book("9781234567890", "L'Odissea", defaultAuthors, 2024, 5);
        CloneB2 = new Book("9791234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        
    }

    @Test
    public void testGetIsbn() {
        assertEquals("9781234567890", defaultBook.getIsbn());
    }

    @Test
    public void testGetTitle() {
        assertEquals("L'Odissea", defaultBook.getTitle());
    }

    @Test
    public void testGetAuthors() {
        ArrayList<Author> authors = defaultBook.getAuthors();
        assertFalse(authors.isEmpty());           // La lista autori non dovrebbe essere vuota
        assertTrue(authors.contains(author1));    //  La lista autori deve contenere l'author1. [Se la get fallisce,controllare prima che la testAdd abbia avuto successo]
    }

    @Test
    public void testGetPublishingYear() {
        assertEquals(2024, defaultBook.getPublishingYear());

    }

    @Test
    public void testGetCopies() {
        assertEquals(5, defaultBook.getCopies());
    }

    @Test
    public void testIsAdopted() {
        assertTrue(defaultBook.isAdopted());

    }

    /*@Test
    public void testCheckAvailability() {

    }
    */
    
    @Test
    public void testSetTitle() {
        String newTitle = "Titolo Rivisitato";
        defaultBook.setTitle(newTitle);
        assertEquals(newTitle, defaultBook.getTitle());
    }

    @Test
    public void testSetStatus() {
        defaultBook.setStatus(AdoptionStatus.NOT_ADOPTED);
        assertEquals(AdoptionStatus.NOT_ADOPTED, defaultBook.isAdopted());
    }

    @Test
    public void testSetPublishingYear() {
        int newYear = 2025;
        defaultBook.setPublishingYear(newYear);
        assertEquals(newYear, defaultBook.getPublishingYear());
    }

    @Test
    public void testSetCopies() {
        int newCopies = 36;
        defaultBook.setCopies(newCopies);
        assertEquals(newCopies, defaultBook.getCopies());
   
    }

    @Test
    public void testAddAuthor() {
        assertTrue(defaultBook.addAuthor(author2));
        assertTrue(defaultBook.getAuthors().contains(author2));
        assertEquals(2, defaultBook.getAuthors().size());
    }

    @Test
    public void testToString() {
    }

    @Test
    public void testEquals() {
        Book CloneB1 = new Book("9791234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        Book CloneB2 = new Book("9791234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        assertTrue(CloneB1.equals(CloneB2));
        Book CloneB3 = new Book("9771234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        assertFalse(CloneB1.equals(CloneB3));
        
    }

    @Test
    public void testCompareTo() {
        
    }

    @Test
    public void testHashCode() {
        Book CloneB1 = new Book("9791234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        Book CloneB2 = new Book("9791234567890", "Robinson Crusoe", defaultAuthors, 1719, 5);
        assertEquals(CloneB1.hashCode(), CloneB2.hashCode());
    }
    
}
