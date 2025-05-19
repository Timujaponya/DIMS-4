package com.sau.bm.model;

import com.sau.bm.dtos.LoanDTO;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="loanDate")
    private LocalDate loanDate;

    @Column(name="amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public LoanDTO viewAsLoanDTO() {
        return new LoanDTO(id, loanDate, amount);
    }

    public Loan() {
    }

    public Loan(long id, LocalDate loanDate, BigDecimal amount) {
        this.id = id;
        this.loanDate = loanDate;
        this.amount = amount;
    }

    public Loan( LocalDate loanDate, BigDecimal amount,Customer customer, Account account) {

        this.loanDate = loanDate;
        this.amount = amount;
        this.customer = customer;
        this.account = account;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", loanDate=" + loanDate +
                ", amount=" + amount +
                ", customer=" + customer +
                ", account=" + account +
                '}';
    }
}
