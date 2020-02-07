package pl.ultimasolution.loan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ultimasolution.loan.exception.ResourceNotFoundException;
import pl.ultimasolution.loan.model.Loans;
import pl.ultimasolution.loan.repository.LoanRepository;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanRestController {

    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<Loans> getAllLoans() {
        return loanRepository.findAll();
    }

    @PostMapping("/loans")
    public Loans createLoan(@Valid @RequestBody Loans loan) {
        return loanRepository.save(loan);
    }

    @GetMapping("/loans/{id}")
    public Loans getLoanById(@PathVariable(value = "id") Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loans", "id", loanId));
    }

    @PutMapping("/loans/prolong/{id}")
    public Loans prolongLoan(@PathVariable(value = "id") Long loanId,
                             @Valid @RequestBody Loans loanDetails) {

        Loans loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loans", "id", loanId));

        // prolong loan by 14 days
        if (loan.getProlongedTerm()) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date()); // Now use today date.
            c.add(Calendar.DATE, 14); // Adding 14 days
            loan.setTermAt(c.getTime());
        } else {
            // inform about one prolonged time
        }
        Loans updatedLoan = loanRepository.save(loan);
        return updatedLoan;
    }

    @DeleteMapping("/loans/{id}")
    public ResponseEntity<?> deleteLoan(@PathVariable(value = "id") Long loanId) {
        Loans loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loans", "id", loanId));

        loanRepository.delete(loan);

        return ResponseEntity.ok().build();
    }
}
