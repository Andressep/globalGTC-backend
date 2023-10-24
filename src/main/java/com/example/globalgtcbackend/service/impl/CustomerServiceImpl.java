package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.utils.QuotationReportGeneration;
import com.example.globalgtcbackend.mappers.QuotationMapper;
import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Customer;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.models.entity.Quotation;
import com.example.globalgtcbackend.repository.ICustomerDao;
import com.example.globalgtcbackend.repository.IProductDao;
import com.example.globalgtcbackend.repository.IQuotationDao;
import com.example.globalgtcbackend.service.ICustomerService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.*;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private ICustomerDao customerDao;

    @Autowired
    private IQuotationDao quotationDao;

    @Autowired
    private IProductDao productDao;
    @Autowired
    private QuotationMapper quotationMapper;
    @Autowired
    private QuotationReportGeneration quotationReportGeneration;

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
    public List<QuotationDTO> getAllQuotations() {
        return quotationMapper.toDTOList(quotationDao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public QuotationDTO findQuotationDTOById(Integer id) {
        Quotation quotation = quotationDao.findById(id).orElse(null);
        if (quotation != null) {
            return quotationMapper.toDTO(quotation);
        }
        return null; // O maneja el caso en el que no se encuentra la cotización
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
    public Optional<Quotation> findQuotationsByCustomerId(Integer customerId) {
        return quotationDao.findByCustomerCustomerId(customerId);
    }

    @Override
    public byte[] exportToPdf(Integer id) throws JRException, FileNotFoundException {
        return quotationReportGeneration.exportToPdf(findQuotationDTOById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByProductName(String term) {
        return productDao.findByProductName(term);
    }


}
