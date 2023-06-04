package com.codigo.demo.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.domain.dto.PurchaseHistoryReqDTO;
import com.codigo.demo.domain.entity.PurchaseHistory;
import com.codigo.demo.domain.repository.PurchaseHistoryRepository;
import com.codigo.demo.domain.service.PurchaseHistoryService;
import com.codigo.demo.modelmapper.GenericModelMapper;
import com.codigo.demo.utils.JsonUtils;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

	private Logger logger = LoggerFactory.getLogger(PurchaseHistoryServiceImpl.class);

	private final PurchaseHistoryRepository purchaseHistoryRepository;

	private final GenericModelMapper mapper;

	public PurchaseHistoryServiceImpl(final PurchaseHistoryRepository purchaseHistoryRepository,
			final GenericModelMapper mapper) {
		this.purchaseHistoryRepository = purchaseHistoryRepository;
		this.mapper = mapper;
	}

	@Override
	public ServiceResult<Long> save(PurchaseHistoryReqDTO dto) {
		ServiceResult<Long> result = new ServiceResult<>();
		try {
			var purchaseHistory = new PurchaseHistory();
			purchaseHistory = this.mapper.convertDTOToEntity(dto, PurchaseHistory.class);
			purchaseHistory = this.purchaseHistoryRepository.save(purchaseHistory);
			logger.info("Purchase history response : {}", JsonUtils.toJSON(dto));
		} catch (Exception e) {
			logger.info("Error occur in create purchase history", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Page<PurchaseHistory>> retrieveAll(Pageable pageable) {
		ServiceResult<Page<PurchaseHistory>> result = new ServiceResult<>();
		try {
			var purchseHis = this.purchaseHistoryRepository.findAll(pageable);
			logger.info("Purchase history list response : {}", JsonUtils.toJSON(purchseHis));

			result.success(purchseHis);
		} catch (Exception e) {
			logger.info("Error occur in create purchase history", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

}
