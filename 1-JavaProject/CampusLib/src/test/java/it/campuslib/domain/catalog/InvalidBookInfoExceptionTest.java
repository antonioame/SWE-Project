package it.campuslib.domain.catalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class InvalidBookInfoExceptionTest {
    
    @Test
    public void testDefaultConstructor() {
        InvalidBookInfoException ex = new InvalidBookInfoException();
        assertNull(ex.getMessage());  //Il messaggio di default dovrebbe essere nullo
        assertNotNull(ex); // L'eccezione non dovrebbe essere nulla
    }

    @Test
    public void testMessageConstructor() {
        String expmsg = "Isbn non valido.";
        InvalidBookInfoException ex = new InvalidBookInfoException(expmsg);
        
        assertNotNull(ex);
        // Il messaggio dell'eccezione deve corrispondere a quello fornito.
        assertEquals(expmsg, ex.getMessage()); 
    }
}
