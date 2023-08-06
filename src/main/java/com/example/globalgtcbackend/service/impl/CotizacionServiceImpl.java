package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Cotizacion;
import com.example.globalgtcbackend.repository.ICotizacionDao;
import com.example.globalgtcbackend.service.ICotizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotizacionServiceImpl implements ICotizacionService {

    @Autowired
    private ICotizacionDao cotizacionDao;
    @Override
    public List<Cotizacion> allCotizacions() {
        return cotizacionDao.findAll();
    }

    @Override
    public Cotizacion findCotizacionById(Integer id) {
        return cotizacionDao.findById(id).orElse(null);
    }

    @Override
    public Cotizacion saveCotizacion(Cotizacion cotizacion) {
        return cotizacionDao.save(cotizacion);
    }

    @Override
    public void deleteCliente(Integer id) {
        cotizacionDao.deleteById(id);
    }
}
