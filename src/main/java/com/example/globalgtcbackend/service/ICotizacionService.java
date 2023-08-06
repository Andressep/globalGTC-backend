package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.entity.Cotizacion;

import java.util.List;

public interface ICotizacionService {
    public List<Cotizacion> allCotizacions();
    public Cotizacion findCotizacionById(Integer id);
    public Cotizacion saveCotizacion(Cotizacion cotizacion);
    public void deleteCliente(Integer id);
}
