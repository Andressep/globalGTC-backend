package com.example.globalgtcbackend;

import com.example.globalgtcbackend.models.dto.QuotationDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuotationReportGeneration {


    public byte[] exportToPdf(QuotationDTO quotationDTO) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReport(quotationDTO));
    }

    private JasperPrint getReport(QuotationDTO quotationDto) throws JRException, FileNotFoundException {
        File imgLogo = ResourceUtils.getFile("classpath:images/globalGTC.jpg");
        Map<String, Object> params = new HashMap<>();
        params.put("quotationDto", quotationDto);
        params.put("companyLogo", new FileInputStream(imgLogo));
        params.put("ds", new JRBeanCollectionDataSource(quotationDto.getQuotationDetailsList()));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:Global.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());
        return report;
    }

}
