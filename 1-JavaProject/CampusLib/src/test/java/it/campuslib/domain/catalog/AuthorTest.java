package it.campuslib.domain.catalog;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author luca
 */
public class AuthorTest {
    
    public AuthorTest() {
    }

    @Test
    public void testConstructor() {
        
        Author a1 = new Author("Steinbeck", "John");
        Author a2 = new Author("Focault", "Michel");
        
        assertNotNull(a1);
        assertNotNull(a2);
    }
    
}
