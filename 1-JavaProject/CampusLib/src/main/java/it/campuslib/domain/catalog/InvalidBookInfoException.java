package it.campuslib.domain.catalog;


public class InvalidBookInfoException extends Exception {

    public InvalidBookInfoException() {
    }
    
    public InvalidBookInfoException (String msg) {
        super(msg);
    }
}

