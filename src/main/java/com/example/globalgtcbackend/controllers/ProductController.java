package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.dto.ProductDTO;
import com.example.globalgtcbackend.models.entity.Category;
import com.example.globalgtcbackend.models.entity.Product;
import com.example.globalgtcbackend.service.IProductService;
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
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping(value = "/create", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Product product, BindingResult result) {
        Product newProduct = null;
        Category category = productService.findCategoryById(product.getCategory().getCategoryId());
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
            product.setCategory(category);
            newProduct = productService.saveProduct(product);
        } catch (DataAccessException e) {
            response.put("message", "Error while inserting into the database");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Product created successfully");
        response.put("product", newProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<ProductDTO> getAll() {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Product product = null;
        Map<String, Object> response = new HashMap<>();

        try {
            product = productService.findProductById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while searching the database");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (product == null) {
            response.put("message", "Product ID: ".concat(id.toString()).concat(" does not exist in the database"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Product product, BindingResult result, @PathVariable Integer id) {
        Product currentProduct = productService.findProductById(id);
        Product updatedProduct = null;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "The field " + err.getField() + " " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (currentProduct == null) {
            response.put("message", "Error: Could not edit, Product ID: ".concat(id.toString().concat(" does not exist in the database.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setSize(product.getSize());
            currentProduct.setWeight(product.getWeight());
            updatedProduct = productService.saveProduct(currentProduct);
        } catch (DataAccessException e) {
            response.put("message", "Error while updating the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The product has been updated successfully");
        response.put("product", updatedProduct);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            productService.deleteProduct(id);
        } catch (DataAccessException e) {
            response.put("message", "Error while deleting from the database.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "The product has been deleted successfully!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
