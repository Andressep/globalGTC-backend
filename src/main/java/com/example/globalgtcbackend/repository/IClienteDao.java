package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteDao extends JpaRepository<Cliente, Integer> {
}
