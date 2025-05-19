package com.sau.bm.service;

import com.sau.bm.dtos.CustomerDTO;
import com.sau.bm.exception.ResourceAlreadyExistsException;
import com.sau.bm.model.Customer;
import com.sau.bm.exception.ErrorMessages;
import com.sau.bm.exception.ResourceNotFoundException;
import com.sau.bm.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomerEntities() {
        return customerRepository.findAll();
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_CUSTOMER_NOT_FOUND + ": " + id))
                .viewAsCustomerDTO();
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(Customer::viewAsCustomerDTO).toList();
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        // Yeni müşteri için ID kontrolü yapma, zaten otomatik oluşacak
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setCity(customerDTO.getCity());

        return customerRepository.save(customer).viewAsCustomerDTO();
    }

    public CustomerDTO updateCustomer(long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));

        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setCity(customerDTO.getCity());

        return customerRepository.save(customer).viewAsCustomerDTO();
    }

    public void deleteCustomer(long id) {
        customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_CUSTOMER_NOT_FOUND + ": " + id));
        customerRepository.deleteById(id);
    }
}
