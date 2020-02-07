package pl.ultimasolution.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ultimasolution.loan.model.Loans;


@Repository
public interface LoanRepository extends JpaRepository<Loans, Long> {

}
