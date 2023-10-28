package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Product;
import net.sf.jasperreports.engine.JRException;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.List;

public interface IQuotationService {

    // Quotations
    List<QuotationDTO> getAllQuotations();
    @Transactional(readOnly = true)
    QuotationDTO findQuotationDTOById(Integer id);

    QuotationDTO saveQuotation(QuotationDTO quotation);
    void deleteQuotation(Integer id);
    byte[] exportToPdf(Integer id) throws JRException, FileNotFoundException;

    @Transactional(readOnly = true)
    List<Product> findByProductName(String term);
}
