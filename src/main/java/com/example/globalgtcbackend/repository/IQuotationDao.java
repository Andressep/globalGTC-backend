package com.example.globalgtcbackend.repository;

import com.example.globalgtcbackend.models.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IQuotationDao extends JpaRepository<Quotation, Integer> {
    /*@Query(value = "SELECT quotations FROM quotations WHERE quotation.customer.customerId=:customerId", nativeQuery = true)
    Optional<Quotation> findQuotationByCustomerIdAndQuotationId(Integer customerId);*/

    Optional<Quotation> findByCustomerCustomerId(Integer customerId);
}
