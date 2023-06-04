package com.codigo.demo.domain.service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.common.status.PromoCodeStatus;
import com.codigo.demo.domain.dto.PromoDetailReqDTO;

public interface PromoDetailService {

	public ServiceResult<Long> save(PromoDetailReqDTO dto);

	public ServiceResult<Boolean> validatePromoCodeStatus(String promoCode);

	public ServiceResult<Boolean> generatePromoCodes(PromoDetailReqDTO dto, Integer numberOfPromoCode);

}
