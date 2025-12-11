package it.campuslib.domain.users;

/**
 *
 * @author luca
 */
public class InvalidUserInfoException extends Exception{
    
    public InvalidUserInfoException() {
    
    }
    
    public InvalidUserInfoException(String msg) {
    
        super(msg);
    }
}
