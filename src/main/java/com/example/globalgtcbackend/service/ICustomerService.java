package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.entity.Customer;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.models.entity.Quotation;

import java.util.List;

public interface ICustomerService {

    List<Customer> getAllCustomers();
    Customer findCustomerById(Integer id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Integer id);

    // Quotations
    List<Quotation> getAllQuotations();
    Quotation findQuotationById(Integer id);
    Quotation saveQuotation(Quotation quotation);
    void deleteQuotation(Integer id);

    // Native Queries
    List<Product> findByProductName(String term);
}
