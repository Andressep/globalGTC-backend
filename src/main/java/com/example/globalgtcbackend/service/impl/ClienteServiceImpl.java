package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Cliente;
import com.example.globalgtcbackend.models.entity.Cotizacion;
import com.example.globalgtcbackend.models.entity.Producto;
import com.example.globalgtcbackend.repository.IClienteDao;
import com.example.globalgtcbackend.repository.ICotizacionDao;
import com.example.globalgtcbackend.repository.IProductoDao;
import com.example.globalgtcbackend.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {
    @Autowired
    private IClienteDao clienteDao;
    @Autowired
    private ICotizacionDao cotizacionDao;
    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> allClients() {
        return clienteDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findClienteById(Integer id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    @Transactional
    public void deleteCliente(Integer id) {
        clienteDao.deleteById(id);
    }

    // Cotizaciones

    @Override
    @Transactional(readOnly = true)
    public List<Cotizacion> allCotizacions() {
        return cotizacionDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cotizacion findCotizacionById(Integer id) {
        return cotizacionDao.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public Cotizacion saveCotizacion(Cotizacion cotizacion) {
        return cotizacionDao.save(cotizacion);
    }

    @Override
    @Transactional()
    public void deleteCotizacion(Integer id) {
        cotizacionDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombreProducto(String term) {
        return productoDao.findByNombre(term);
    }
}
