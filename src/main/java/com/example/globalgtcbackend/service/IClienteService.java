package com.example.globalgtcbackend.service;

import com.example.globalgtcbackend.models.entity.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> allClients();
    public Cliente findClienteById(Integer id);
    public Cliente saveCliente(Cliente cliente);
    public void deleteCliente(Integer id);
}
