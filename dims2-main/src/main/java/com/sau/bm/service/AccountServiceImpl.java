package com.sau.bm.service;

import com.sau.bm.dtos.AccountDTO;
import com.sau.bm.exception.ErrorMessages;
import com.sau.bm.exception.InvalidBalanceException;
import com.sau.bm.exception.ResourceAlreadyExistsException;
import com.sau.bm.exception.ResourceNotFoundException;
import com.sau.bm.model.Account;
import com.sau.bm.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountById(long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND + ": " + id)).viewAsAccountDTO();
    }

    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(Account::viewAsAccountDTO).toList();
    }

    public AccountDTO createAccount(AccountDTO accountDTO) {
        if (accountRepository.findById(accountDTO.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_ACCOUNT_ALREADY_EXIST + accountDTO.getId());
        }
        if (accountDTO.getBalance() == null || accountDTO.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidBalanceException("Balance cannot be negative");
        }
        Account account = new Account(accountDTO.getId(), accountDTO.getBranch(), accountDTO.getBalance());
        return accountRepository.save(account).viewAsAccountDTO();
    }

    public AccountDTO updateAccount(long id, AccountDTO accountDTO) {
        // Entity'yi repository'den çekiyoruz.
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND + ": " + id));

        if (accountDTO.getBalance() == null || accountDTO.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidBalanceException("Balance cannot be negative");
        }

        // Entity üzerinde güncelleme yapıyoruz.
        account.setBalance(accountDTO.getBalance());
        account.setBranch(accountDTO.getBranch());

        // Güncellenmiş entity'yi kaydediyoruz.
        Account updated = accountRepository.save(account);
        return updated.viewAsAccountDTO();
    }


    public void deleteAccount(long id) {
        accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_ACCOUNT_NOT_FOUND + ": " + id));
        accountRepository.deleteById(id);
    }
}

