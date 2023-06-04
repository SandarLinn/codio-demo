package com.codigo.demo.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.common.status.EVoucherStatus;
import com.codigo.demo.domain.dto.EVoucherReqDTO;
import com.codigo.demo.domain.entity.EVoucher;
import com.codigo.demo.domain.projection.EVoucherView;
import com.codigo.demo.domain.repository.DiscountPerPaymentRepository;
import com.codigo.demo.domain.repository.EVoucherRepository;
import com.codigo.demo.domain.service.EVoucherService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.modelmapper.GenericModelMapper;
import com.codigo.demo.utils.JsonUtils;

@Service
public class EVoucherServiceImpl implements EVoucherService {

	private Logger logger = LoggerFactory.getLogger(EVoucherServiceImpl.class);

	private final EVoucherRepository eVoucherRepository;

	private final DiscountPerPaymentRepository discountPerPaymentRepository;

	private final GenericModelMapper mapper;

	@Autowired
	public EVoucherServiceImpl(final EVoucherRepository eVoucherRepository, final GenericModelMapper mapper,
			final DiscountPerPaymentRepository discountPerPaymentRepository) {
		this.eVoucherRepository = eVoucherRepository;
		this.discountPerPaymentRepository = discountPerPaymentRepository;
		this.mapper = mapper;
	}

	@Override
	public ServiceResult<Long> save(EVoucherReqDTO dto) {
		ServiceResult<Long> result = new ServiceResult<>();
		try {
			logger.info("E-Voucher create request : {}", JsonUtils.toJSON(dto));
			var discountPayment = this.discountPerPaymentRepository.findById(dto.getDiscountPerPaymentId())
					.orElseThrow(() -> new ApiException("Fail to retrieve discount per payment", "400",
							"No found fordiscount pre payment for id" + dto.getDiscountPerPaymentId()));
			EVoucher eVoucher = new EVoucher();
			eVoucher = this.mapper.convertDTOToEntity(dto, EVoucher.class);
			eVoucher.setId(null);
			eVoucher.setDiscountPerPayment(discountPayment);
			eVoucher = this.eVoucherRepository.save(eVoucher);
			logger.info("E-Voucher create respone : {}", JsonUtils.toJSON(eVoucher));
			result.success(eVoucher.getId());
		} catch (Exception e) {
			logger.info("Error occur in create e-voucher : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Long> update(EVoucherReqDTO dto) {
		ServiceResult<Long> result = new ServiceResult<>();
		try {
			logger.info("E-Voucher update request : {}", JsonUtils.toJSON(dto));
			var eVoucherRes = this.eVoucherRepository.findEVoucherById(dto.getId())
					.orElseThrow(() -> new ApiException("Retrieve  e-voucher by id : {}", "400",
							"No e-voucher found for id " + dto.getId()));

			EVoucher eVoucher = new EVoucher();
			eVoucher = this.mapper.convertDTOToEntity(dto, EVoucher.class);
			eVoucher.setId(eVoucherRes.getId());
			eVoucher.setCreatedAt(eVoucherRes.getcreatedAt());
			eVoucher = this.eVoucherRepository.save(eVoucher);
			logger.info("E-Voucher update respone : {}", JsonUtils.toJSON(eVoucher));
			result.success(eVoucher.getId());
		} catch (Exception e) {
			logger.info("Error occur in update e-voucher : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Long> delete(Long id) {
		ServiceResult<Long> result = new ServiceResult<>();
		try {
			this.eVoucherRepository.deleteById(id);
			result.success(id);
		} catch (Exception e) {
			logger.info("Error occur in delete e-voucher : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Page<EVoucherView>> retrieveAll(String title, Pageable pageable) {
		ServiceResult<Page<EVoucherView>> result = new ServiceResult<>();
		try {
			var eVouchers = this.eVoucherRepository.findEVoucherInfo(title, pageable);
			logger.info("E-Voucher list response : {}", JsonUtils.toJSON(eVouchers));
			result.success(eVouchers);
		} catch (Exception e) {
			logger.info("Error occur in retrieve e-voucher : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<EVoucherView> getById(Long id) {
		ServiceResult<EVoucherView> result = new ServiceResult<>();
		try {
			var eVoucher = this.eVoucherRepository.findEVoucherById(id).orElseThrow(
					() -> new ApiException("Retrieve  e-voucher by id : {}", "400", "No e-voucher found for id " + id));
			logger.info("Retrieve e-voucher by id response : {}", JsonUtils.toJSON(eVoucher));
			result.success(eVoucher);
		} catch (Exception e) {
			logger.info("Error occur in get e-voucher by id : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> updateStatus(Long id, EVoucherStatus status) {
		ServiceResult<Boolean> result = new ServiceResult<>();
		try {
			var eVoucher = this.eVoucherRepository.findById(id).orElseThrow(
					() -> new ApiException("Retrieve  e-voucher by id : {}", "400", "No e-voucher found for id " + id));
			eVoucher.setStatus(status);
			eVoucher = this.eVoucherRepository.save(eVoucher);
			logger.info("Update e-voucher update status response : {}", JsonUtils.toJSON(eVoucher));
			result.success(Boolean.TRUE);
		} catch (Exception e) {
			logger.info("Error occur in update e-voucher status : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

}
