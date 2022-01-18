package pl.ultimasolution.loan.model;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
public class Loans implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final long MAX_LOAN = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    @Size(max = 20, min = 3)
    @NotEmpty(message = "Please enter correct name of loan")
    private String LoanName;

    @Column
    @NotNull(message = "Please enter correct amount of loan")
    @Min(0)
    @Max(MAX_LOAN)
    private long Amount;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date TermAt;

    @Column
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

    @Column
    private String HistoryLog;


    public Loans() {
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

    public String getHistoryLog() throws ParseException {
        return this.HistoryLog;
    }

    public void setHistoryLog(String historyLog) {
        this.HistoryLog = historyLog;
    }

    public Long getId() {
        return Id;
    }

    public long getAmount() {
        return this.Amount;
    }

    public void setAmount(Long amount) {
        this.Amount = amount;
    }

    public void setTermAt() {
        java.util.Date now = new Date();
        Calendar myCal = Calendar.getInstance();
        myCal.setTime(now);
        myCal.add(Calendar.MONTH, +1);
        this.TermAt = myCal.getTime();
    }

    public void setUpdateAt() {
        this.UpdatedAt = new Date();
    }

    public Long getLoanId() {
        return this.Id;
    }

    public String getLoanName() {
        return this.LoanName;
    }

    public void setLoanName(String loanName) {
        this.LoanName = loanName;
    }

    public String getIp() {
        return this.Ip;
    }

    public void setIp(String ip) {
        this.Ip = ip;
    }

    public Date getCreatedAt() {
        return this.CreatedAt;
    }

    public void setCreatedAt(Date date) {
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
        return Id.equals(loans.Id) &&
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

    public String toString() {
        return "Loans(Id: " + this.Id + ", LoanName:" + this.LoanName + ", Amount: " + this.Amount + ", ProlongedTerm:" + this.ProlongedTerm +
                ", TermAt:" + this.TermAt + ", UpdatedAt:" + this.UpdatedAt +
                ", CreatedAt:" + this.CreatedAt + ", Ip:" + this.Ip + ")";
    }

    public Date getUpdateAt() {
        return UpdatedAt;
    }

    public void setUpdateAt(Date date) {
        this.UpdatedAt = date;
    }
}