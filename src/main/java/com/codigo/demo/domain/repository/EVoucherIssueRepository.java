package com.codigo.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codigo.demo.domain.entity.EVoucherIssue;

@Repository
public interface EVoucherIssueRepository extends JpaRepository<EVoucherIssue, Long> {

}
