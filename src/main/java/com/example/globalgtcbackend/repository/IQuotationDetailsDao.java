package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.QuotationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuotationDetailsDao extends JpaRepository<QuotationDetails, Integer> {
}
