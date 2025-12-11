package it.campuslib.domain.users;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


 
/**
 *
 * @author luca
 */
public class UserTest {
    
    private User utente1, utente2, utente3;
    
    
    public UserTest() {
    }

    @BeforeEach
    public void setUp() {
    
        utente1 = new User("Mario", "Rossi", "1112701345", "mario.rossi@studenti.unisa.it");
        utente2 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        utente3 = new User("Luca", "Verdi", "1000111222", "luca.verdi@studenti.unisa.it");
    }
    
    
    @Test
    public void testConstructorValid() {
    
        
        User u1 = new User("Mario", "Rossi", "0612701345", "mario.rossi@unisa.it"); //Formati corretti
        
        User u2 = new User("Mario", "Bianchi", "0612455", "mario.bianchi@studenti.unisa.it"); //Formato matricola errato
        
        User u3 = new User("Mario", "Bianchi", "0612701345", "mario.bianchi@unina.it"); //Formato email errato
        
        assertNotNull(u1);
        assertNotNull(u2);
        assertNotNull(u3);
    }
        

    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetEnrollmentID() {
        
        assertEquals("1112701345", utente1.getEnrollmentID());
        assertEquals("1234567890", utente2.getEnrollmentID());
        assertEquals("1000111222", utente3.getEnrollmentID());
       
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetSetEmail() {
        
        User utente4 = new User("Maria", "Roma", "0612710122", "maria.roma@unisa.it");
        
        utente4.setEmail("maria.roma@studenti.unisa.it");
        
        assertEquals("mario.rossi@studenti.unisa.it", utente1.getEmail());
        assertEquals("andrea.bianchi@unisa.it", utente2.getEmail());
        assertEquals("luca.verdi@studenti.unisa.it", utente3.getEmail());
        assertEquals("maria.roma@studenti.unisa.it", utente4.getEmail());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetSetStatus() {
        
        
        utente1.setStatus(UserStatus.ACTIVE);
        utente2.setStatus(UserStatus.INACTIVE);
        utente3.setStatus(UserStatus.ACTIVE);
        
        assertEquals(UserStatus.ACTIVE, utente1.getStatus());
        assertEquals(UserStatus.INACTIVE, utente2.getStatus());
        assertEquals(UserStatus.ACTIVE, utente3.getStatus());
        
    }

    

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testIsActive() {
        
        utente1.setStatus(UserStatus.ACTIVE);
        utente2.setStatus(UserStatus.INACTIVE);
        utente3.setStatus(UserStatus.ACTIVE);
        
        assertTrue(utente1.isActive());
        assertFalse(utente2.isActive());
        assertTrue(utente3.isActive());
    }


    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGetMaxLoans() {
    
        assertEquals(3, utente1.getMaxLoans());
        assertEquals(3, utente2.getMaxLoans());
        assertEquals(3, utente3.getMaxLoans());
    }
    
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testEquals() {
        
        User utente4 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        
        assertFalse(utente1.equals(utente2));
        assertTrue(utente2.equals(utente4));
        assertFalse(utente1.equals(utente3));
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testHashCode() {
        
        User utente4 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        
        assertEquals(utente2.hashCode(), utente4.hashCode());
    }

    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testCompareTo() {
        
        //utente3<utente2
        User utente4 = new User("Andrea", "Bianchi", "1234567890", "andrea.bianchi@unisa.it");
        
        assertTrue(utente3.compareTo(utente2) < 0);
        assertTrue(utente2.compareTo(utente1) > 0);
        assertTrue(utente2.compareTo(utente4) == 0);
    }
    
}
