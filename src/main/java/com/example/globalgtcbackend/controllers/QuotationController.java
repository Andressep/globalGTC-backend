package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.models.entity.Quotation;

import com.example.globalgtcbackend.service.IQuotationService;
import com.example.globalgtcbackend.utils.EmailService;
import jakarta.mail.Quota;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quotation")
public class QuotationController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private IQuotationService quotationService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody QuotationDTO quotation, BindingResult result) {
        QuotationDTO newQuotation = new QuotationDTO();
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> "Field " + error.getField() + " " + error.getDefaultMessage())
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newQuotation = quotationService.saveQuotation(quotation);
        } catch (DataAccessException e) {
            response.put("message", "Error while inserting into the database");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Quotation created successfully");
        response.put("quotation", newQuotation);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<QuotationDTO> getAll() {
        return quotationService.getAllQuotations();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Integer id) {
        QuotationDTO quotationDTO = null;
        Map<String, Object> response = new HashMap<>();

        try {
            quotationDTO = quotationService.findQuotationDTOById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while searching the database");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (quotationDTO == null) {
            response.put("message", "Quotation with ID: ".concat(id.toString()).concat(" does not exist in the database"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quotationDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/filter/{term}", method = RequestMethod.GET)
    public List<Product> showProductsByName(@PathVariable String term) {
        return quotationService.findByProductName(term);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Quotation quotation, BindingResult result, @PathVariable Integer id) {
        QuotationDTO quotationCurrent = quotationService.findQuotationDTOById(id);
        QuotationDTO quotationUpdated = null;

        Map<String, Object> response = new HashMap<>();

        // TODO: Handle errors.
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "Field " + err.getField() + " " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (quotationCurrent == null) {
            response.put("message", "Error: could not edit, Quotation ID: ".concat(id.toString().concat(" does not exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            quotationCurrent.setCustomerName(quotation.getCustomer().getName());
            quotationCurrent.setCustomerRut(quotation.getCustomer().getRut());
            quotationCurrent.setCustomerPhoneNumber(quotation.getCustomer().getPhone());
            quotationCurrent.setCustomerAddress(quotation.getCustomer().getAddress());
            quotationCurrent.setSalespersonName(quotation.getSalesperson().getName());
            quotationUpdated = quotationService.saveQuotation(quotationCurrent);
        } catch (DataAccessException e) {
            response.put("message", "Error while updating the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Quotation has been updated successfully");
        response.put("quotation", quotationUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            quotationService.deleteQuotation(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while deleting from the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The quotation has been deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/export-pdf/{id}")
    public ResponseEntity<byte[]> exportQuotationToPdf(@PathVariable Integer id) throws JRException, FileNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("quotationReport", "quotationReport.pdf");

        // Llamar al servicio para obtener el informe PDF a partir del ID de la cotización
        byte[] pdfBytes = quotationService.exportToPdf(id);

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @GetMapping("/mail")
    public ResponseEntity<String> sendMail(@RequestParam("quotationId") Integer quotationId, @RequestParam("recipientEmail") String recipientEmail) throws JRException, FileNotFoundException {
        QuotationDTO quotationDTO = quotationService.findQuotationDTOById(quotationId);
        this.emailService.sendQuotationByEmail(quotationDTO, recipientEmail);

        return new ResponseEntity<>("Cotizacion enviada con exito", HttpStatus.OK);
    }
}

