package it.campuslib.domain.users;

public class InvalidUserInfoException extends Exception{
    public InvalidUserInfoException() {
    }

    public InvalidUserInfoException(String msg) {
        super(msg);
    }
}
