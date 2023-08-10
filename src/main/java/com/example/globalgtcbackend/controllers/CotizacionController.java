package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.entity.Cotizacion;
import com.example.globalgtcbackend.models.entity.Producto;
import com.example.globalgtcbackend.service.IClienteService;
import org.apache.coyote.Response;
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
public class CotizacionController {

    @Autowired
    private IClienteService clienteService;

    @RequestMapping(value = "/cotizacion", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Cotizacion cotizacion, BindingResult result) {
        Cotizacion nuevaCotizacion = null;
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
            nuevaCotizacion = clienteService.saveCotizacion(cotizacion);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el insert en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Cotizacion creada con exito");
        response.put("cotizacion", nuevaCotizacion);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/cotizaciones", method = RequestMethod.GET)
    public List<Cotizacion> getAll() {
        return clienteService.allCotizacions();
    }

    @RequestMapping(value = "/cotizacion/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Cotizacion cotizacion = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cotizacion = clienteService.findCotizacionById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al buscar en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cotizacion == null) {
            response.put("message", "La cotizacion ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cotizacion, HttpStatus.OK);
    }

    @RequestMapping(value = "/cotizacion/filtrar/{term}")
    public List<Producto> showProductsByName(@PathVariable String term) {
        return clienteService.findByNombreProducto(term);
    }


    @RequestMapping(value = "/cotizacion/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Cotizacion cotizacion, BindingResult result, @PathVariable Integer id) {
        Cotizacion cotizacionActual = clienteService.findCotizacionById(id);
        Cotizacion cotizacionUpdated = null;

        Map<String, Object> response = new HashMap<>();

        //TODO: Hacer el manejo de errores.
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (cotizacionActual == null) {
            response.put("message", "Error: no se pudo editar, la cotizacion ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            cotizacionActual.setCliente(cotizacion.getCliente());
            cotizacionActual.setVendedor(cotizacion.getVendedor());
            cotizacionActual.setProductos(cotizacion.getProductos());
            cotizacionUpdated = clienteService.saveCotizacion(cotizacionActual);
        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El cliente ha sido actualizado con exito");
        response.put("cotizacion", cotizacionUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value="/cotizacion/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            clienteService.deleteCliente(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La cotizacion fue eliminado con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
