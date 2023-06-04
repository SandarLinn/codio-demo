package com.codigo.demo.domain.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.dto.DiscountPerPaymentResDTO;
import com.codigo.demo.domain.repository.DiscountPerPaymentRepository;
import com.codigo.demo.domain.service.DiscountPerPaymentService;
import com.codigo.demo.modelmapper.GenericModelMapper;
import com.codigo.demo.utils.JsonUtils;

@Service
public class DiscountPerPaymentServiceImpl implements DiscountPerPaymentService {

	private Logger logger = LoggerFactory.getLogger(DiscountPerPaymentServiceImpl.class);

	private final DiscountPerPaymentRepository discountPerPaymentRepository;

	private final GenericModelMapper mapper;

	@Autowired
	public DiscountPerPaymentServiceImpl(final DiscountPerPaymentRepository discountPerPaymentRepository,
			final GenericModelMapper mapper) {
		this.discountPerPaymentRepository = discountPerPaymentRepository;
		this.mapper = mapper;
	}

	@Override
	public ServiceResult<List<DiscountPerPaymentResDTO>> findAll() {
		ServiceResult<List<DiscountPerPaymentResDTO>> result = new ServiceResult<>();
		try {
			List<DiscountPerPaymentResDTO> dtos = new ArrayList<>();
			var payments = this.discountPerPaymentRepository.findAll();
			if (payments != null) {
				payments.forEach(o -> dtos.add(this.mapper.convertEntityToDTO(o, DiscountPerPaymentResDTO.class)));
			}
			logger.info("Discount per payment response dto : {}", JsonUtils.toJSON(dtos));
			result.success(dtos);
		} catch (Exception e) {
			result.fail(e, e.getMessage());
		}
		return result;
	}

}
