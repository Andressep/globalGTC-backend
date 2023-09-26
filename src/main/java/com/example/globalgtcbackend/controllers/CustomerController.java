package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.entity.Customer;
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
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "/create", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Customer customer, BindingResult result) {
        Customer newCustomer = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> "The field " + error.getField() + " " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            newCustomer = customerService.saveCustomer(customer);
        } catch (DataAccessException e) {
            response.put("message", "Error while inserting into the database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Customer created successfully");
        response.put("customer", newCustomer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Customer customer = null;
        Map<String, Object> response = new HashMap<>();

        try {
            customer = customerService.findCustomerById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while searching the database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (customer == null) {
            response.put("message", "Customer ID: ".concat(id.toString()).concat(" does not exist in the database"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Customer customer, BindingResult result, @PathVariable Integer id) {
        Customer currentCustomer = customerService.findCustomerById(id);
        Customer updatedCustomer = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "The field " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentCustomer == null) {
            response.put("message", "Error: Could not edit, Customer ID: ".concat(id.toString().concat(" does not exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            currentCustomer.setName(customer.getName());
            currentCustomer.setRut(customer.getRut());
            currentCustomer.setEmail(customer.getEmail());
            currentCustomer.setQuotations(customer.getQuotations());
            updatedCustomer = customerService.saveCustomer(currentCustomer);
        } catch (DataAccessException e) {
            response.put("message", "Error while updating the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The customer has been updated successfully");
        response.put("customer", updatedCustomer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            customerService.deleteCustomer(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while deleting from the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The customer has been deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
