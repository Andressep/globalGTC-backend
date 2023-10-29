package com.example.globalgtcbackend.utils;

import com.example.globalgtcbackend.models.dto.QuotationDTO;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@Service
@Transactional
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private QuotationReportGeneration quotationReportGeneration;
    @Value("${spring.mail.username}")
    private String email;



    public void sendQuotationByEmail(QuotationDTO quotationDTO, String emailTo) throws JRException, FileNotFoundException {
        MimeMessage message = javaMailSender.createMimeMessage();
        byte[] quotationPdf = quotationReportGeneration.exportToPdf(quotationDTO);

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(email);
            helper.setTo(emailTo);
            helper.setSubject("COT " + quotationDTO.getQuotationCode() + " GTC");
            helper.setText("Cuerpo del correo a enviar");

            ByteArrayDataSource pdfDataSource = new ByteArrayDataSource(quotationPdf, "application/pdf");
            helper.addAttachment("Cotizacion.pdf", pdfDataSource);

            javaMailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar la cotización por correo", e);
        }
    }
}
