package com.codigo.demo.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codigo.demo.domain.entity.EVoucherIssue;

@Repository
public interface EVoucherIssueRepository extends JpaRepository<EVoucherIssue, Long> {
	
	@Query(value = "select * from e_voucher_issue where id = :id", nativeQuery = true)
	Optional<EVoucherIssue> findEVoucherIssueByID(@Param(value = "id") Long id);

}
