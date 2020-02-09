package pl.ultimasolution.loan.exception;

/**
 * projekt:  LoanREST
 * pakckage: pl.ultimasolution.loan.exception
 * autor:    karol
 * data:     2020-02-09
 * <p>
 * description:
 **/
public class OnceProlongedException extends RuntimeException {
    public OnceProlongedException(Long loanId) {
            super("Loan "+ loanId + " can be prolonged once.");
    }
}

