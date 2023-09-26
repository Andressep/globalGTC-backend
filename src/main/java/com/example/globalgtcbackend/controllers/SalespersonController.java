package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.entity.Salesperson;
import com.example.globalgtcbackend.service.ISalespersonService;
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
@RequestMapping("/api/v1/salesperson")
public class SalespersonController {
    @Autowired
    private ISalespersonService salespersonService;

    @RequestMapping(value = "/create", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Salesperson salesperson, BindingResult result) {
        Salesperson newSalesperson = null;
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
            newSalesperson = salespersonService.saveSalesperson(salesperson);
        } catch (DataAccessException e) {
            response.put("message", "Error while inserting into the database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Salesperson created successfully");
        response.put("salesperson", newSalesperson);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Salesperson> getAll() {
        return salespersonService.getAllSalespeople();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Salesperson salesperson = null;
        Map<String, Object> response = new HashMap<>();

        try {
            salesperson = salespersonService.findSalespersonById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while searching the database");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (salesperson == null) {
            response.put("message", "Salesperson ID: ".concat(id.toString()).concat(" does not exist in the database"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(salesperson, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Salesperson salesperson, BindingResult result, @PathVariable Integer id) {
        Salesperson currentSalesperson = salespersonService.findSalespersonById(id);
        Salesperson updatedSalesperson = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "The field " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentSalesperson == null) {
            response.put("message", "Error: Could not edit, Salesperson ID: ".concat(id.toString().concat(" does not exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            currentSalesperson.setName(salesperson.getName());
            updatedSalesperson = salespersonService.saveSalesperson(currentSalesperson);
        } catch (DataAccessException e) {
            response.put("message", "Error while updating the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The salesperson has been updated successfully");
        response.put("salesperson", updatedSalesperson);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            salespersonService.deleteSalesperson(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while deleting from the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The salesperson has been deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
