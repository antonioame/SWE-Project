package it.campuslib.domain.users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author luca
 */
public class InvalidUserInfoExceptionTest {
    
    public InvalidUserInfoExceptionTest() {
    }

    @Test
    public void testConstructorDefault() {
        
        String msg = "Formato non valido.";
        
        InvalidUserInfoException ex = new InvalidUserInfoException();
    }
    
}
