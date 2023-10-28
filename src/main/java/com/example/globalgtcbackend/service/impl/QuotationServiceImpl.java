package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.exception.ResourceNoFoundException;
import com.example.globalgtcbackend.mappers.Mapper;
import com.example.globalgtcbackend.models.dto.ProductQuotationDTO;
import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.*;
import com.example.globalgtcbackend.repository.IProductDao;
import com.example.globalgtcbackend.repository.IQuotationDao;
import com.example.globalgtcbackend.service.IQuotationService;
import com.example.globalgtcbackend.utils.QuotationReportGeneration;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class QuotationServiceImpl implements IQuotationService {

    @Autowired
    private IQuotationDao quotationDao;
    @Autowired
    private IProductDao productDao;
    @Autowired
    private QuotationReportGeneration quotationReportGeneration;
    @Autowired
    @Qualifier("QuotationMapper")
    private Mapper<Quotation, QuotationDTO> quotationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<QuotationDTO> getAllQuotations() {
        var quotations = quotationDao.findAll();
        List<QuotationDTO> quotationList = new ArrayList<>();
        Optional.ofNullable(quotations).ifPresent(
                quotation -> quotation.forEach(
                        eachQuotation -> quotationList.add(quotationMapper.transform(eachQuotation))
                )
        );
        return quotationList;
    }

    @Override
    @Transactional(readOnly = true)
    public QuotationDTO findQuotationDTOById(Integer id) {
        var quotationDTO = quotationDao.findById(id);
        if (quotationDTO.isPresent()) return quotationMapper.transform(quotationDTO.get());
        throw new ResourceNoFoundException("Quotation ID is invalid");
    }
    @Override
    @Transactional()
    public QuotationDTO saveQuotation(QuotationDTO quotations) {
        Quotation quotation = quotationMapper.transformBack(quotations);

        Customer customer = new Customer();
        customer.setName(quotations.getCustomerName());
        customer.setRut(quotations.getCustomerRut());
        customer.setAddress(quotations.getCustomerAddress());
        customer.setPhone(quotations.getCustomerPhoneNumber());

        Salesperson salesperson = new Salesperson();
        salesperson.setName(quotations.getSalespersonName());

        List<QuotationDetails> quotationDetailsList = new ArrayList<>();
        for (ProductQuotationDTO productDTO : quotations.getQuotationDetailsList()) {
            // Crear y configurar QuotationDetails
            QuotationDetails details = new QuotationDetails();
            details.setProduct(productDao.findById(productDTO.getProductId()).orElse(null));
            details.setQuantity(productDTO.getQuantity());

            quotationDetailsList.add(details);
        }
        // Asignar los objetos relacionados
        quotation.setCustomer(customer);
        quotation.setSalesperson(salesperson);
        quotation.setQuotationDetails(quotationDetailsList);

        return quotationMapper.transform(quotationDao.save(quotation));
    }

    @Override
    @Transactional()
    public void deleteQuotation(Integer id) {
        quotationDao.deleteById(id);
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
