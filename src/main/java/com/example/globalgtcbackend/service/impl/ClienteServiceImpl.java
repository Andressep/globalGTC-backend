package com.example.globalgtcbackend.service.impl;

import com.example.globalgtcbackend.models.entity.Cliente;
import com.example.globalgtcbackend.repository.IClienteDao;
import com.example.globalgtcbackend.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {
    @Autowired
    private IClienteDao clienteDao;

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
}
