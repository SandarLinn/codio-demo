package com.codigo.demo.domain.service;

import java.util.List;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.dto.DiscountPerPaymentResDTO;

public interface DiscountPerPaymentService {

	public ServiceResult<List<DiscountPerPaymentResDTO>> findAll();

}
