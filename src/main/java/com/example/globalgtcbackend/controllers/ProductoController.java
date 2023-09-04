package com.example.globalgtcbackend.controllers;

import com.example.globalgtcbackend.models.dto.ProductoDTO;
import com.example.globalgtcbackend.models.entity.Categoria;
import com.example.globalgtcbackend.models.entity.Producto;
import com.example.globalgtcbackend.service.IProductoService;
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
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @RequestMapping(value = "/producto", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Producto producto, BindingResult result) {
        Producto nuevoProducto = null;
        Categoria categoria = productoService.findCategoriaById(producto.getCategoria().getCategoria_id());
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
            producto.setCategoria(categoria);
            nuevoProducto = productoService.saveProducto(producto);
        } catch (DataAccessException e) {
            response.put("message", "Error al realizar el insert en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "Producto creado con exito");
        response.put("producto", nuevoProducto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/productos", method = RequestMethod.GET)
    public List<ProductoDTO> getAll() {
        return productoService.allProductos();
    }

    @RequestMapping(value = "/producto/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Producto producto = null;
        Map<String, Object> response = new HashMap<>();

        try {
            producto = productoService.findProductoById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al buscar en la base de datos");
            response.put("Error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (producto == null) {
            response.put("message", "El producto ID: ".concat(id.toString()).concat(" no existe en la base de datos"));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @RequestMapping(value = "/producto/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Producto producto, BindingResult result, @PathVariable Integer id) {
        Producto productoActual = productoService.findProductoById(id);
        Producto productoUpdated = null;

        Map<String, Object> response = new HashMap<>();

        //TODO: Hacer el manejo de errores.
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream().map(
                            err -> "El campo " + err.getField() + " " + err.getDefaultMessage())
                    .toList();
            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (productoActual == null) {
            response.put("message", "Error: no se pudo editar, la producto ID: ".concat(id.toString().concat(" no existe en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            productoActual.setName(producto.getName());
            productoActual.setPrice(producto.getPrice());
            productoActual.setSize(producto.getSize());
            productoActual.setWeight(producto.getWeight());
            productoUpdated = productoService.saveProducto(productoActual);
        } catch (DataAccessException e) {
            response.put("message", "Error al actualizar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "El producto ha sido actualizado con exito");
        response.put("producto", productoUpdated);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value="/producto/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            productoService.deleteProducto(id);
        } catch (DataAccessException e) {
            response.put("message", "Error al eliminar la base de datos.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El producto fue eliminado con exito!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
