package com.sau.bm.dtos;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
public class LoanDTO {
    private Long id;
    private LocalDate loanDate;
    private BigDecimal amount;
    private CustomerDTO customerDTO;
    private AccountDTO accountDTO;

    public LoanDTO(long id, LocalDate loanDate, BigDecimal amount) {
        this.id = id;
        this.loanDate = loanDate;
        this.amount = amount;
    }

    public LoanDTO(long id, LocalDate loanDate, BigDecimal amount, CustomerDTO customerDTO, AccountDTO accountDTO) {
        this.id = id;
        this.loanDate = loanDate;
        this.amount = amount;
        this.customerDTO = customerDTO;
        this.accountDTO = accountDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

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

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }
}

