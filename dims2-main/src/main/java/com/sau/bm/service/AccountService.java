package com.sau.bm.service;

import com.sau.bm.dtos.AccountDTO;

import java.util.List;

public interface AccountService {
    public List<AccountDTO> getAllAccounts();
    public AccountDTO getAccountById(long id);
    public AccountDTO createAccount(AccountDTO accountDTO);
    public AccountDTO updateAccount(long id, AccountDTO accountDTO);
    public void deleteAccount(long id);
}
