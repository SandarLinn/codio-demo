package com.codigo.demo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.entity.UserMaster;

/**
 * @Author sandar linn
 * @CreatedAt June 3, 2023
 */
public interface UserService {
	// extends UserDetailsService

	public ServiceResult<Long> save(String name, String password, String loginName, String phone, String email);

	public ServiceResult<Long> update(Long id, String name, String password, String loginName, String phone,
			String email);

	public ServiceResult<UserMaster> getById(Long id);

	public ServiceResult<Page<UserMaster>> findAll(Pageable pageable);

}
