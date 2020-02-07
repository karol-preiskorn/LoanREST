package pl.ultimasolution.loan.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * projekt:  LoanREST
 * pakckage: pl.ultimasolution.LoanREST.model
 * autor:    karol
 * data:     2020-02-06
 * <p>
 * description: Loan Entity
 **/

@Entity
@Table(name = "loans")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"CreatedAt", "UpdatedAt"},
        allowGetters = true)
public class Loans implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final long MAX_LOAN = 100000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @Size(max = 20, min = 3)
    @NotEmpty(message = "Please enter name of loan")
    private String LoanName;

    @Column
    @NotNull(message = "Please enter amount of loan")
    private Number Amount;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TermAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date UpdatedAt;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date CreatedAt;

    @Column
    private String Ip;

    @Column
    private boolean ProlongedTerm;

    protected Loans() {
    }

    public Loans(String LoanName, Long amount, String ip) {
        this.setLoanName(LoanName);
        this.setAmount(amount);
        this.setCreatedAt(new Date());
        this.setProlongedTerm(false);
        setTermAt(); // loan for month
    }

    public Loans(String LoanName, Date term, Long amount, String ip) {
        this.setAmount(amount);
        this.setLoanName(LoanName);
        this.setCreatedAt(new Date());
        this.setProlongedTerm(false);
        this.setTermAt(term); // loan for month
    }

    private Number getAmount() {
        return this.Amount;
    }

    private void setAmount(Long amount) {
        this.Amount = amount;
    }

    private void setTermAt() {
        java.util.Date now = new Date();
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(now);
        myCal.add(Calendar.MONTH, +1);
        this.TermAt = myCal.getTime();
    }

    private void setUpdateAt(Date date) {
        this.UpdatedAt = date;
    }

    public Long getLoanId() {
        return this.Id;
    }

    public String getLoanName() {
        return this.LoanName;
    }

    private void setLoanName(String loanName) {
        this.LoanName = loanName;
    }

    public String getIp() {
        return this.Ip;
    }

    private void setIp(String ip) {
        this.Ip = ip;
    }

    public Date getCreatedAt() {
        return this.CreatedAt;
    }

    private void setCreatedAt(Date date) {
        this.CreatedAt = date;
    }

    public Date getTermAt() {
        return this.TermAt;
    }

    public void setTermAt(Date date) {
        this.TermAt = date;
    }

    public boolean getProlongedTerm() {
        return this.ProlongedTerm;
    }

    public void setProlongedTerm(boolean prolongedTerm) {
        this.ProlongedTerm = prolongedTerm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loans loans = (Loans) o;
        return Id == loans.Id &&
                Amount == loans.Amount &&
                ProlongedTerm == loans.ProlongedTerm &&
                LoanName.equals(loans.LoanName) &&
                TermAt.equals(loans.TermAt) &&
                Objects.equals(UpdatedAt, loans.UpdatedAt) &&
                CreatedAt.equals(loans.CreatedAt) &&
                Ip.equals(loans.Ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, LoanName, Amount, TermAt, UpdatedAt, CreatedAt, Ip, ProlongedTerm);
    }
}