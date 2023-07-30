package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.entity.Vendedor;
import com.example.globalgtcbackend.service.IVendedorService;
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
@RequestMapping("/api/v1")
public class VendedorController {
    @Autowired
    private IVendedorService vendedorService;

    @RequestMapping(value = "/vendedor", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Vendedor vendedor, BindingResult result) {
        Vendedor nuevoVendedor = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(error -> "El campo " + error.getField() + " " + error.getDefaultMessage())
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            nuevoVendedor = vendedorService.saveVendedor(vendedor);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el insert en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Vendedor creado con exito");
        response.put("vendedor", nuevoVendedor);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/vendedores", method = RequestMethod.GET)
    public List<Vendedor> getAll() {
        return vendedorService.allVendedor();
    }

    @RequestMapping(value = "/vendedor/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Vendedor vendedor = null;
        Map<String, Object> response = new HashMap<>();

        try {
            vendedor = vendedorService.findVendedorById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al buscar en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (vendedor == null) {
            response.put("message", "El vendedor ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vendedor, HttpStatus.OK);
    }

    @RequestMapping(value = "/vendedor/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Vendedor vendedor, BindingResult result, @PathVariable Integer id) {
        Vendedor vendedorActual = vendedorService.findVendedorById(id);
        Vendedor vendedorUpdated = null;

        Map<String, Object> response = new HashMap<>();

        //TODO: Hacer el manejo de errores.
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (vendedorActual == null) {
            response.put("message", "Error: no se pudo editar, el vendedor ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            vendedorUpdated.setName(vendedor.getName());
            vendedorUpdated = vendedorService.saveVendedor(vendedorActual);
        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El cliente ha sido actualizado con exito");
        response.put("vendedor", vendedorUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value="/vendedor/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            vendedorService.deleteVendedor(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El vendedor fue eliminado con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
