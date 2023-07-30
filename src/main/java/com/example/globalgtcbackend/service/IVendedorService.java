package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.entity.Vendedor;

import java.util.List;

public interface IVendedorService {
    public List<Vendedor> allVendedor();
    public Vendedor findVendedorById(Integer id);
    public Vendedor saveVendedor(Vendedor vendedor);
    public void deleteVendedor(Integer id);
}
