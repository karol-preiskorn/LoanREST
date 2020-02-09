package pl.ultimasolution.loan.exception;

/**
 * projekt:  LoanREST
 * pakckage: pl.ultimasolution.loan.exception
 * autor:    karol
 * data:     2020-02-09
 * <p>
 * description:
 **/
public class LoanOutOfTimeException extends RuntimeException {
    public LoanOutOfTimeException(String txt) {
        super(txt);
    }
}

