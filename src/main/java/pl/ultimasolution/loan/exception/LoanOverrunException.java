package pl.ultimasolution.loan.exception;

/**
 * projekt:  LoanREST
 * pakckage: pl.ultimasolution.loan.exception
 * autor:    karol
 * data:     2020-02-08
 * <p>
 * description:
 **/
public class LoanOverrunException extends RuntimeException {
    public LoanOverrunException(String exception) {
        super(exception);
    }
}


