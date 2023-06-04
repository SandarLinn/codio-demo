package com.codigo.demo.domain.service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.dto.EVoucherIssueReqDTO;

public interface EVoucherIssueService {

	public ServiceResult<Long> save(EVoucherIssueReqDTO dto);

	public ServiceResult<Boolean> paidWithEvoucher(Long id, Double amount);

}
