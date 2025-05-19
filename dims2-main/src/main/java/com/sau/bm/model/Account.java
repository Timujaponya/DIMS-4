package com.sau.bm.model;

import com.sau.bm.dtos.AccountDTO;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 64)
    private String branch;

    @Column(length = 8)
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Loan> loans;

    public AccountDTO viewAsAccountDTO() {
        return new AccountDTO(id, branch, balance);
    }

    public Account() {
    }

    public Account(long id, String branch, BigDecimal balance) {
        this.id = id;
        this.branch = branch;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                ", balance='" + balance + '\'' +
                ", loans=" + loans +
                '}';
    }
}
