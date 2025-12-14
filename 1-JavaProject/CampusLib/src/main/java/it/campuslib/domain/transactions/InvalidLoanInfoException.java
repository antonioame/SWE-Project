package it.campuslib.domain.transactions;

public class InvalidLoanInfoException extends Exception{

    
    public InvalidLoanInfoException() {
    }

    
    public InvalidLoanInfoException(String msg) {
        super(msg);
    }
}
