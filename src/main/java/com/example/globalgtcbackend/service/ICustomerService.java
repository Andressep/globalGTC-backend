package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Customer;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.models.entity.Quotation;
import net.sf.jasperreports.engine.JRException;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;

public interface ICustomerService {

    List<Customer> getAllCustomers();
    Customer findCustomerById(Integer id);
    Customer saveCustomer(Customer customer);
    void deleteCustomer(Integer id);

    // Quotations
    List<QuotationDTO> getAllQuotations();
    @Transactional(readOnly = true)
    QuotationDTO findQuotationDTOById(Integer id);

    Quotation saveQuotation(Quotation quotation);
    void deleteQuotation(Integer id);
    public byte[] exportToPdf(Integer id)throws JRException, FileNotFoundException;

    // Native Queries
    List<Product> findByProductName(String term);
}
