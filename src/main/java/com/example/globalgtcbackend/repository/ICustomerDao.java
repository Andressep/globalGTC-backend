package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerDao extends JpaRepository<Customer, Integer> {
}
