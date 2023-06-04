package com.codigo.demo.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.dto.PurchaseHistoryReqDTO;
import com.codigo.demo.domain.entity.PurchaseHistory;

public interface PurchaseHistoryService {

	public ServiceResult<Long> save(PurchaseHistoryReqDTO dto);

	public ServiceResult<Page<PurchaseHistory>> retrieveAll(Pageable pageable);

}
