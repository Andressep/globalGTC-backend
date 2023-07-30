package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.entity.Cliente;
import com.example.globalgtcbackend.service.IClienteService;
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
public class ClienteController {
    @Autowired
    private IClienteService clienteService;

    @RequestMapping(value = "/cliente", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestParam Cliente cliente, BindingResult result) {
        Cliente nuevoCliente = null;
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
            nuevoCliente = clienteService.saveCliente(cliente);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el insert en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Cliente creado con exito");
        response.put("cliente", nuevoCliente);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.GET)
    public List<Cliente> getAll() {
        return clienteService.allClients();
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteService.findClienteById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al buscar en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cliente == null) {
            response.put("message", "El cliente ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @RequestMapping(value = "/cliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Cliente cliente, BindingResult result, @PathVariable Integer id) {
        Cliente clienteActual = clienteService.findClienteById(id);
        Cliente clienteUpdated = null;

        Map<String, Object> response = new HashMap<>();

        //TODO: Hacer el manejo de errores.
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (clienteActual == null) {
            response.put("message", "Error: no se pudo editar, el cliente ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            clienteUpdated.setName(cliente.getName());
            clienteUpdated.setRut(cliente.getRut());
            clienteUpdated.setEmail(cliente.getEmail());
            clienteUpdated.setCotizaciones(cliente.getCotizaciones());
            clienteUpdated = clienteService.saveCliente(clienteActual);
        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El cliente ha sido actualizado con exito");
        response.put("cliente", clienteUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value="/cliente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.deleteCliente(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente eliminado con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}