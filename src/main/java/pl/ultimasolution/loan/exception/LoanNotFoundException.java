package pl.ultimasolution.loan.exception;

/**
 * projekt:  LoanREST
 * pakckage: pl.ultimasolution.loan.exception
 * autor:    karol
 * data:     2020-02-09
 * <p>
 * description:
 **/
public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long loanId) {
        super("Loan Id: "+ loanId + " cannot be found.");
    }
}


