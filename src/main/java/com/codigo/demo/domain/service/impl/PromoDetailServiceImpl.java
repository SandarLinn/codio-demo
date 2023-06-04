package com.codigo.demo.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.common.status.PromoCodeStatus;
import com.codigo.demo.domain.dto.PromoDetailReqDTO;
import com.codigo.demo.domain.entity.PromoDetail;
import com.codigo.demo.domain.repository.PromoDetailRepository;
import com.codigo.demo.domain.service.PromoDetailService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.modelmapper.GenericModelMapper;
import com.codigo.demo.utils.BarCodeGeneratorUtil;
import com.codigo.demo.utils.JsonUtils;
import com.codigo.demo.utils.PromoCodeUtils;

@Service
public class PromoDetailServiceImpl implements PromoDetailService {

	private Logger logger = LoggerFactory.getLogger(PromoDetailServiceImpl.class);

	private final PromoDetailRepository promoDetailRepository;
	private final GenericModelMapper mapper;

	public PromoDetailServiceImpl(final PromoDetailRepository promoDetailRepository, final GenericModelMapper mapper) {
		this.promoDetailRepository = promoDetailRepository;
		this.mapper = mapper;
	}

	@Override
	public ServiceResult<PromoDetail> save(PromoDetailReqDTO dto) {
		ServiceResult<PromoDetail> result = new ServiceResult<>();
		try {
			PromoDetail promoCode = new PromoDetail();
			promoCode = this.mapper.convertDTOToEntity(dto, PromoDetail.class);
			promoCode.setId(null);
			promoCode.setPromoCode(PromoCodeUtils.generatePromoCode());
			promoCode.setQrCode(BarCodeGeneratorUtil.getBase64QRImage(promoCode.getPromoCode()));
			promoCode = this.promoDetailRepository.save(promoCode);
			logger.info("Promo code created response : {}", JsonUtils.toJSON(promoCode));

			result.success(promoCode);
		} catch (Exception e) {
			logger.error("Error occur in create promo code : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> generatePromoCodes(PromoDetailReqDTO dto, Integer numberOfPromoCode) {
		ServiceResult<Boolean> result = new ServiceResult<>();
		try {
			List<PromoDetail> promoCodes = new ArrayList<>();
			for (int i = 0; i < numberOfPromoCode; i++) {
				PromoDetail promoCode = new PromoDetail();
				promoCode = this.mapper.convertDTOToEntity(dto, PromoDetail.class);
				promoCode.setId(null);
				promoCode.setPromoCode(PromoCodeUtils.generatePromoCode());
				promoCode.setQrCode(BarCodeGeneratorUtil.getBase64QRImage(promoCode.getPromoCode()));
				promoCodes.add(promoCode);
			}
			this.promoDetailRepository.saveAll(promoCodes);
			result.success(Boolean.TRUE);
		} catch (Exception e) {
			logger.error("Error occur in generate promo code : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> validatePromoCodeStatus(String promoCode) {
		ServiceResult<Boolean> result = new ServiceResult<>();
		try {
			var promoDetail = this.promoDetailRepository.findPromoDetailByPromoCode(promoCode)
					.orElseThrow(() -> new ApiException("Fail to retrieve promo detail ", "400",
							"No promo detail for promo code " + promoCode));
			logger.error("Promo detail response : {}", JsonUtils.toJSON(promoDetail));

			if (PromoCodeStatus.ACTIVE.equals(promoDetail.getStatus())) {
				result.success(Boolean.TRUE);
			} else {
				result.success(Boolean.FALSE);
			}
		} catch (Exception e) {
			logger.error("Error occur in create promo validate status : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

}
