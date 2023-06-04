package com.codigo.demo.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codigo.demo.domain.entity.UserMaster;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {

	Optional<UserMaster> findUserMasterByLoginName(String loginName);

	//Optional<UserMaster> findUserMasterByEmail(String email);

	@Query(value = "select * from user_master ", countQuery = "select count(*) from user_master", nativeQuery = true)
	Page<UserMaster> findAllUser(Pageable pageable);

}
