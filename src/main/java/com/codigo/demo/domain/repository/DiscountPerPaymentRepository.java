package com.codigo.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codigo.demo.domain.entity.DiscountPerPayment;

@Repository
public interface DiscountPerPaymentRepository extends JpaRepository<DiscountPerPayment, Long> {

}
