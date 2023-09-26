package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Customer;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.models.entity.Quotation;
import com.example.globalgtcbackend.repository.ICustomerDao;
import com.example.globalgtcbackend.repository.IProductDao;
import com.example.globalgtcbackend.repository.IQuotationDao;
import com.example.globalgtcbackend.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private IQuotationDao quotationDao;

    @Autowired
    private IProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findCustomerById(Integer id) {
        return customerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer id) {
        customerDao.deleteById(id);
    }

    // Quotations

    @Override
    @Transactional(readOnly = true)
    public List<Quotation> getAllQuotations() {
        return quotationDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Quotation findQuotationById(Integer id) {
        return quotationDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Quotation saveQuotation(Quotation quotation) {
        return quotationDao.save(quotation);
    }

    @Override
    @Transactional()
    public void deleteQuotation(Integer id) {
        quotationDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByProductName(String term) {
        return productDao.findByProductName(term);
    }
}
