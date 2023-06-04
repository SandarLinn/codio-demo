package com.codigo.demo.domain.service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.dto.PromoDetailReqDTO;
import com.codigo.demo.domain.entity.PromoDetail;

public interface PromoDetailService {

	public ServiceResult<PromoDetail> save(PromoDetailReqDTO dto);

	public ServiceResult<Boolean> validatePromoCodeStatus(String promoCode);

	public ServiceResult<Boolean> generatePromoCodes(PromoDetailReqDTO dto, Integer numberOfPromoCode);

}
