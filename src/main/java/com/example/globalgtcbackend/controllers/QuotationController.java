package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.dto.QuotationDTO;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.models.entity.Quotation;

import com.example.globalgtcbackend.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/quotation")
public class QuotationController {

    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Quotation quotation, BindingResult result) {
        Quotation newQuotation = null;
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
            newQuotation = customerService.saveQuotation(quotation);
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
        return customerService.getAllQuotations();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Quotation quotation = null;
        Map<String, Object> response = new HashMap<>();

        try {
            quotation = customerService.findQuotationById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while searching the database");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (quotation == null) {
            response.put("message", "Quotation with ID: ".concat(id.toString()).concat(" does not exist in the database"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(quotation, HttpStatus.OK);
    }

    @RequestMapping(value = "/filter/{term}")
    public List<Product> showProductsByName(@PathVariable String term) {
        return customerService.findByProductName(term);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Quotation quotation, BindingResult result, @PathVariable Integer id) {
        Quotation quotationCurrent = customerService.findQuotationById(id);
        Quotation quotationUpdated = null;

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
            quotationCurrent.setCustomer(quotation.getCustomer());
            quotationCurrent.setSalesperson(quotation.getSalesperson());
            quotationCurrent.setQuotationDetails(quotation.getQuotationDetails());
            quotationUpdated = customerService.saveQuotation(quotationCurrent);
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
            customerService.deleteCustomer(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while deleting from the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The quotation has been deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

