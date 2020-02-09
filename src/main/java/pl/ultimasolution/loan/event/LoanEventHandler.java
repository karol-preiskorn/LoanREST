package pl.ultimasolution.loan.event;

import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import pl.ultimasolution.loan.model.Loans;

import java.util.logging.Logger;

/**
 * projekt:  LoanREST
 * pakckage: pl.ultimasolution.loan.event
 * autor:    karol
 * data:     2020-02-09
 * <p>
 * description: event handler for loan entity
 **/

@RepositoryEventHandler
public class LoanEventHandler {
    protected Logger logger = Logger.getLogger("Class LoanEventHandler");

    public LoanEventHandler() {
        super();
    }

    @HandleBeforeCreate
    public void handleLoanBeforeCreate(Loans loan) {
        logger.info("Inside Loan Before Create ....");
        String name = loan.getLoanName();
    }

    @HandleAfterCreate
    public void handleAuthorAfterCreate(Loans loan) {
        logger.info("Inside Loan After Create ....");
        String name = loan.getLoanName();
    }
}