package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.entity.Cliente;
import com.example.globalgtcbackend.models.entity.Cotizacion;
import com.example.globalgtcbackend.models.entity.Producto;

import java.util.List;

public interface IClienteService {
    public List<Cliente> allClients();
    public Cliente findClienteById(Integer id);
    public Cliente saveCliente(Cliente cliente);
    public void deleteCliente(Integer id);

    // Cotizaciones
    public List<Cotizacion> allCotizacions();
    public Cotizacion findCotizacionById(Integer id);
    public Cotizacion saveCotizacion(Cotizacion cotizacion);
    public void deleteCotizacion(Integer id);

    // Consultas nativas
    public List<Producto> findByNombreProducto(String term);

}
