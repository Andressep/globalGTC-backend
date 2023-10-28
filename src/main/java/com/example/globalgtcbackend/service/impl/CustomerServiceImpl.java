package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Customer;
import com.example.globalgtcbackend.repository.ICustomerDao;
import com.example.globalgtcbackend.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerDao customerDao;
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
}
