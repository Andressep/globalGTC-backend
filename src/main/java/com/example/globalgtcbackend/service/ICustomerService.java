package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.entity.Customer;
import java.util.List;

public interface ICustomerService {

    List<Customer> getAllCustomers();
    Customer findCustomerById(Integer id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Integer id);
}
