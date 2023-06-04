package com.codigo.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codigo.demo.domain.entity.PurchaseHistory;

@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {

}
