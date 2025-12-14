package it.campuslib.domain.transactions;

/**
 *
 * @author luca
 */
public class InvalidLoanInfoException extends Exception{

    
    public InvalidLoanInfoException() {
    }

    
    public InvalidLoanInfoException(String msg) {
        super(msg);
    }
}
