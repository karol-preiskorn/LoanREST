package pl.ultimasolution.loan.controller;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ultimasolution.loan.exception.*;
import pl.ultimasolution.loan.model.Loans;
import pl.ultimasolution.loan.repository.LoanRepository;
import pl.ultimasolution.loan.tools.Tools;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LoanRestController {
    private static final long MAX_LOAN = 1000;
    private static final int LOAN_LENGTH = 14;
    private final Tools tools = new Tools();

    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<Loans> getAllLoans() {
        return loanRepository.findAll();
    }

    /**
     * Create loan entity
     * set setTermAt +14 until now
     *
     * @param loan
     * @return
     */
    @PostMapping("/loans")
    public Loans createLoan(@Valid @RequestBody Loans loan) throws UnknownHostException {
        if(loan.getAmount() > MAX_LOAN) {
            System.out.println("You can take loan amount between 0 and " + MAX_LOAN + ".");
            throw new LoanOverrunException(" " + loan.getAmount());
        }
        loan.setTermAt(getLoanTerm());
        if(tools.checkRiskyCurentTime()) {
            String logTxt = "Your loan " + loan.getLoanName() + " cannot be take between (00:00 - 06:00) now is " + getCurentTime();
            System.out.println(logTxt);
            throw new LoanOutOfTimeException(logTxt);
        }
        loan.setCreatedAt(tools.getCurentTime());
        loan.setUpdateAt(null);
        loan.setIp(getHostAddress());
        return loanRepository.save(loan);
    }

    protected String getHostAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    protected Date getLoanTerm() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, LOAN_LENGTH); // Adding 14 days
        return c.getTime();
    }

    protected Date getCurentTime() {
        return tools.getCurentTime();
    }

    protected String  getCurentIp() throws UnknownHostException {
        return tools.getCurentIp();
    }

    @GetMapping("/loans/{id}")
    public Loans getLoanById(@PathVariable(value = "id") Long Id) {
        return loanRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Loans", "id", Id));
    }

    @PutMapping("/loans/prolong/{id}")
    public Loans prolongLoan(@PathVariable(value = "id") Long Id) throws UnknownHostException, ParseException {

        Loans loan = loanRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Loans", "id", Id));

        // prolong loan by 14 days
        if (! loan.getProlongedTerm()) {
            loan.setTermAt(getLoanTerm());
            loan.setIp(getHostAddress());
            loan.setProlongedTerm(true);
        } else {
            String historyLog = "prolonged can be use once.";
            loan.setHistoryLog(loan.getHistoryLog() + " " + getCurentTime() + " " + getCurentIp() + " " + historyLog + "|");
            throw new OnceProlongedException(Id);
        }
        return loanRepository.save(loan);
    }

    @DeleteMapping("/loans/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable(value = "id") Long Id) {
        Loans loan = loanRepository.findById(Id)
                .orElseThrow(() -> new LoanNotFoundException(Id));
        loanRepository.delete(loan);
        return ResponseEntity.ok().build();
    }
}
