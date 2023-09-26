package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuotationDao extends JpaRepository<Quotation, Integer> {
}
