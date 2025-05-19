package com.sau.bm.service;

import com.sau.bm.dtos.AccountDTO;
import com.sau.bm.dtos.CustomerDTO;
import com.sau.bm.dtos.LoanDTO;
import com.sau.bm.model.Account;
import com.sau.bm.exception.ErrorMessages;
import com.sau.bm.exception.InvalidAmountException;
import com.sau.bm.exception.ResourceAlreadyExistsException;
import com.sau.bm.exception.ResourceNotFoundException;
import com.sau.bm.model.Customer;
import com.sau.bm.model.Loan;
import com.sau.bm.repository.LoanRepository;
import com.sau.bm.repository.AccountRepository;
import com.sau.bm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public LoanServiceImpl(LoanRepository loanRepository, AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.loanRepository = loanRepository;
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    public LoanDTO getLoanById(long id) {
        Loan l = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_LOAN_NOT_FOUND + ": " + id));
        LoanDTO loanDTO = new LoanDTO(l.getId(), l.getLoanDate(), l.getAmount());
        CustomerDTO customerDTO = new CustomerDTO(l.getCustomer().getId(), l.getCustomer().getName(), l.getCustomer().getAddress(), l.getCustomer().getCity());
        loanDTO.setCustomerDTO(customerDTO);
        AccountDTO accountDTO = new AccountDTO(l.getAccount().getId(), l.getAccount().getBranch(), l.getAccount().getBalance());
        loanDTO.setAccountDTO(accountDTO);
        return loanDTO;
    }

    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream().map(Loan::viewAsLoanDTO).toList();
    }

    public LoanDTO createLoan(LoanDTO loanDTO) {
        Account account = accountRepository.findById(loanDTO.getAccountDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + loanDTO.getAccountDTO().getId()));

        Customer customer = customerRepository.findById(loanDTO.getCustomerDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + loanDTO.getCustomerDTO().getId()));

        if (loanDTO.getAmount() == null || loanDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Amount must be greater than zero");
        }

        if (loanDTO.getAmount().compareTo(account.getBalance()) > 0) {
            throw new InvalidAmountException("Amount cannot exceed account balance");
        }

        Loan loan = new Loan();
        loan.setLoanDate(loanDTO.getLoanDate());
        loan.setAmount(loanDTO.getAmount());
        loan.setAccount(account);
        loan.setCustomer(customer);

        Loan saved = loanRepository.save(loan);
        return saved.viewAsLoanDTO();
    }


    public LoanDTO updateLoan(long id, LoanDTO loanDTO) {
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_LOAN_NOT_FOUND + ": " + id));

        Account account = accountRepository.findById(loanDTO.getAccountDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_ACCOUNT_NOT_FOUND + ": " + loanDTO.getAccountDTO().getId()));

        Customer customer = customerRepository.findById(loanDTO.getCustomerDTO().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessages.ERROR_CUSTOMER_NOT_FOUND + ": " + loanDTO.getCustomerDTO().getId()));

        if (loanDTO.getAmount() == null || loanDTO.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Amount cannot be negative");
        }

        if (loanDTO.getAmount().compareTo(account.getBalance()) > 0) {
            throw new InvalidAmountException("Amount cannot exceed account balance");
        }

        existingLoan.setLoanDate(loanDTO.getLoanDate());
        existingLoan.setAmount(loanDTO.getAmount());
        existingLoan.setAccount(account);
        existingLoan.setCustomer(customer);

        Loan updated = loanRepository.save(existingLoan);
        return updated.viewAsLoanDTO();
    }

    public void deleteLoan(long id) {
        loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_LOAN_NOT_FOUND + ": " + id));
        loanRepository.deleteById(id);
    }
}