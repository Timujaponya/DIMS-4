package com.sau.bm.service;

import com.sau.bm.dtos.LoanDTO;

import java.util.List;

public interface LoanService {
    public List<LoanDTO> getAllLoans();
    public LoanDTO getLoanById(long id);
    public LoanDTO createLoan(LoanDTO loanDTO);
    public LoanDTO updateLoan(long id, LoanDTO loanDTO);
    public void deleteLoan(long id);
}
