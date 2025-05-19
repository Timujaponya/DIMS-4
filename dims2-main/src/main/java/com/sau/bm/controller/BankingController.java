package com.sau.bm.controller;
import com.sau.bm.dtos.TransferDTO;
import com.sau.bm.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sau.bm.model.Account;
import com.sau.bm.model.Customer;
import com.sau.bm.model.Loan;
import com.sau.bm.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/banking")
@Controller
@RequiredArgsConstructor
public class BankingController {

    private final BankingService bankingService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    // ---------- ACCOUNTS ----------
    @GetMapping("/accounts")
    public String accountsPage(Model model){
        model.addAttribute("accounts", bankingService.getAllAccounts());
        // Loan modalında müşteri seçmek için customers da lazım
        model.addAttribute("customers", bankingService.getAllCustomers());
        return "accounts";
    }

    @PostMapping("/accounts/add")
    public String addAccount(@ModelAttribute Account account, RedirectAttributes redirectAttributes){
        bankingService.saveAccount(account);
        redirectAttributes.addFlashAttribute("message", "Account created successfully");
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/update")
    public String updateAccount(@ModelAttribute Account account, RedirectAttributes redirectAttributes){
        bankingService.saveAccount(account);
        redirectAttributes.addFlashAttribute("message", "Account updated successfully");
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/delete")
    public String deleteAccount(@RequestParam Long id, RedirectAttributes redirectAttributes){
        bankingService.deleteAccountById(id);
        redirectAttributes.addFlashAttribute("message", "Account deleted successfully");
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/loan")
    public String loanFromAccount(@RequestParam Long accountId,
                                  @RequestParam Long customerId,
                                  @RequestParam String loanDate,  // "2025-03-20" gibi gelir
                                  @RequestParam String amount,
                                  RedirectAttributes redirectAttributes){
        Loan loan = new Loan();
        loan.setAccount(bankingService.getAccountById(accountId).orElse(null));
        loan.setCustomer(bankingService.getCustomerById(customerId).orElse(null));

        LocalDate date = LocalDate.parse(loanDate);
        loan.setLoanDate(date);

        loan.setAmount(new BigDecimal(amount));
        bankingService.saveLoan(loan);
        redirectAttributes.addFlashAttribute("message", "Successfully loaned to account");
        return "redirect:/accounts";
    }

    // ---------- CUSTOMERS ----------
    @GetMapping("/customers")
    public String customersPage(Model model){
        model.addAttribute("customers", bankingService.getAllCustomers());
        model.addAttribute("accounts", bankingService.getAllAccounts());
        return "customers";
    }

    @PostMapping("/customers/add")
    public String addCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes){
        bankingService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("message", "Customer created successfully");
        return "redirect:/customers";
    }

    @PostMapping("/customers/update")
    public String updateCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes){
        bankingService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("message", "Customer updated successfully");
        return "redirect:/customers";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam Long id, RedirectAttributes redirectAttributes){
        bankingService.deleteCustomerById(id);
        redirectAttributes.addFlashAttribute("message", "Customer deleted successfully");
        return "redirect:/customers";
    }

    @PostMapping("/customers/loan")
    public String loanFromCustomer(@RequestParam Long customerId,
                                   @RequestParam Long accountId,
                                   @RequestParam String loanDate, // "2025-03-20"
                                   @RequestParam String amount,
                                   RedirectAttributes redirectAttributes){
        Loan loan = new Loan();
        loan.setCustomer(bankingService.getCustomerById(customerId).orElse(null));
        loan.setAccount(bankingService.getAccountById(accountId).orElse(null));

        LocalDate date = LocalDate.parse(loanDate);
        loan.setLoanDate(date);

        loan.setAmount(new BigDecimal(amount));
        bankingService.saveLoan(loan);
        redirectAttributes.addFlashAttribute("message", "Successfully loaned to customer");
        return "redirect:/customers";
    }

    // ---------- LOANS ----------
    @GetMapping("/loans")
    public String loansPage(Model model){
        model.addAttribute("loans", bankingService.getAllLoans());
        model.addAttribute("customers", bankingService.getAllCustomers());
        model.addAttribute("accounts", bankingService.getAllAccounts());
        return "loans";
    }

    @PostMapping("/loans/update")
    public String updateLoan(@RequestParam Long id,
                             @RequestParam Long customerId,
                             @RequestParam Long accountId,
                             @RequestParam String loanDate,  // "2025-03-20"
                             @RequestParam String amount,
                             RedirectAttributes redirectAttributes){

        Loan loan = bankingService.getLoanById(id).orElse(new Loan());
        loan.setCustomer(bankingService.getCustomerById(customerId).orElse(null));
        loan.setAccount(bankingService.getAccountById(accountId).orElse(null));

        LocalDate date = LocalDate.parse(loanDate);
        loan.setLoanDate(date);

        loan.setAmount(new BigDecimal(amount));
        bankingService.saveLoan(loan);
        redirectAttributes.addFlashAttribute("message", "Loan updated successfully");
        return "redirect:/loans";
    }

    @PostMapping("/loans/delete")
    public String deleteLoan(@RequestParam Long id, RedirectAttributes redirectAttributes){
        bankingService.deleteLoanById(id);
        redirectAttributes.addFlashAttribute("message", "Loan deleted successfully");
        return "redirect:/loans";
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferDTO transferDTO) {
        try {
            bankingService.transfer(
                    transferDTO.getFromAccountId(),
                    transferDTO.getToAccountId(),
                    transferDTO.getAmount()
            );
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Transfer failed: " + e.getMessage());
        }
    }

}
