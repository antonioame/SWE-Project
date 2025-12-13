package it.campuslib.domain.users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvalidUserInfoExceptionTest {
    // FIXME: Correggere questa classe di test
    @Test
    public void testConstructorDefault() {
        String msg = "Formato non valido.";
        InvalidUserInfoException ex = new InvalidUserInfoException();
    }
}
