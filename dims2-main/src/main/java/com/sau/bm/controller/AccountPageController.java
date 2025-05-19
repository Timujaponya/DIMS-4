package com.sau.bm.controller;

import com.sau.bm.service.AccountService;
import com.sau.bm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountPageController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        model.addAttribute("customers", customerService.getAllCustomers()); // Loan modal için
        return "accounts"; // resources/templates/accounts.html
    }
}
