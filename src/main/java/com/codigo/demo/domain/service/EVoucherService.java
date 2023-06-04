package com.codigo.demo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.common.status.EVoucherStatus;
import com.codigo.demo.domain.dto.EVoucherReqDTO;
import com.codigo.demo.domain.projection.EVoucherView;

public interface EVoucherService {

	public ServiceResult<Long> save(EVoucherReqDTO dto);

	public ServiceResult<Long> update(EVoucherReqDTO dto);

	public ServiceResult<Long> delete(Long id);

	public ServiceResult<Page<EVoucherView>> retrieveAll(String title, Pageable pageable);

	public ServiceResult<EVoucherView> getById(Long id);
	
	public ServiceResult<Boolean> updateStatus(Long id, EVoucherStatus status);

}
