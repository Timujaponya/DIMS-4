package com.sau.bm.controller;

import com.sau.bm.dtos.LoanDTO;
import com.sau.bm.model.Loan;
import com.sau.bm.service.CustomerService;
import com.sau.bm.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/loan")
public class LoanController {
    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final LoanService loanService;
    private final CustomerService customerService;

    public LoanController(LoanService loanService, CustomerService customerService) {
        this.loanService = loanService;
        this.customerService = customerService;
    }

    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<LoanDTO>> getLoans() {
        return new ResponseEntity<>(loanService.getAllLoans(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json")
    public ResponseEntity<LoanDTO> getLoan(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(loanService.getLoanById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<LoanDTO> addLoan(@RequestBody LoanDTO loanDTO) {
        return new ResponseEntity<>(loanService.createLoan(loanDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<LoanDTO> updateLoan(@PathVariable Long id, @RequestBody LoanDTO loanDTO) {
        if (id == null || id == 0 || loanDTO == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(loanService.updateLoan(id, loanDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Loan> deleteLoan(@PathVariable Long id) {
        if (id == null || id == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        loanService.deleteLoan(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}