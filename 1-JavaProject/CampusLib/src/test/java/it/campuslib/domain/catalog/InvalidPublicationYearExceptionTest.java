package it.campuslib.domain.catalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class InvalidPublicationYearExceptionTest {

    @Test
    public void testSomeMethod() {
    }
    
    @Test
    public void testDefaultConstructor() {
        InvalidPublicationYearException ex = new InvalidPublicationYearException();
        assertNull(ex.getMessage());  //Il messaggio di default dovrebbe essere nullo
        assertNotNull(ex); // L'eccezione non dovrebbe essere nulla
    }
    
    @Test
    public void testMessageConstructor() {
        String expmsg = "Anno di pubblicazione non valido.";
        InvalidPublicationYearException ex = new InvalidPublicationYearException(expmsg);
        
        assertNotNull(ex);
        // Il messaggio dell'eccezione deve corrispondere a quello fornito.
        assertEquals(expmsg, ex.getMessage());
    }
}
