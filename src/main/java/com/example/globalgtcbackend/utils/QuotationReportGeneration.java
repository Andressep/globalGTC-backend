package com.example.globalgtcbackend.utils;

import com.example.globalgtcbackend.models.dto.QuotationDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuotationReportGeneration {


    public byte[] exportToPdf(QuotationDTO quotationDTO) throws JRException, FileNotFoundException {
        JasperPrint report = getReport(quotationDTO);
        String reportPath = createReportDirectory(quotationDTO.getQuotationCode());


        //String fileName = "/Users/andressepulveda/Desktop/JasperReport/COT " + quotationDTO.getQuotationCode() + " GTC" + ".pdf";

        JasperExportManager.exportReportToPdfFile(report, reportPath);

        return JasperExportManager.exportReportToPdf(report);
    }
    public JasperPrint getReport(QuotationDTO quotationDto) throws JRException, FileNotFoundException {
        File imgLogo = ResourceUtils.getFile("classpath:images/globalGTC.jpg");
        Map<String, Object> params = new HashMap<>();
        params.put("quotationDto", quotationDto);
        params.put("companyLogo", new FileInputStream(imgLogo));
        params.put("customerName", quotationDto.getCustomerName().toUpperCase());
        params.put("customerRut", quotationDto.getCustomerRut());
        params.put("customerPhoneNumber", quotationDto.getCustomerPhoneNumber());
        params.put("customerAddress", quotationDto.getCustomerAddress().toUpperCase());
        params.put("salespersonName", quotationDto.getSalespersonName().toUpperCase());
        params.put("createdAt", quotationDto.getCreatedAt());
        params.put("subTotal", quotationDto.getSubTotal());
        params.put("tax", quotationDto.getTax());
        params.put("totalPayment", quotationDto.getTotalPayment());
        params.put("totalWeight", quotationDto.getTotalWeight());
        params.put("ds", new JRBeanCollectionDataSource(quotationDto.getQuotationDetailsList()));

        JasperPrint report = JasperFillManager.fillReport(JasperCompileManager.compileReport(
                ResourceUtils.getFile("classpath:Global.jrxml")
                        .getAbsolutePath()), params, new JREmptyDataSource());
        return report;
    }
    public String createReportDirectory(String quotationCode) {
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + "/Desktop";
        String folderPath = desktopPath + "/JasperReportTest";
        String fileName = "COT " + quotationCode + " GTC.pdf";

        File folder = new File(folderPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Carpeta creada exitosamente.");
            } else {
                System.err.println("Error al crear la carpeta.");
            }
        } else {
            System.out.println("La carpeta ya existe.");
        }

        return folderPath + "/" + fileName;
    }

}
