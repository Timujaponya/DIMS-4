package com.sau.bm.service;

import com.sau.bm.dtos.CustomerDTO;
import com.sau.bm.model.Customer;

import java.util.List;

public interface CustomerService {

    // ✔ Burada artık wrapper Long kullanılıyor
    CustomerDTO getCustomerById(Long id);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO createCustomer(CustomerDTO dto);

    CustomerDTO updateCustomer(long id, CustomerDTO dto);
    public List<Customer> getAllCustomerEntities();
    void deleteCustomer(long id);
}