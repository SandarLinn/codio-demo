package com.codigo.demo.domain.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.common.status.EVoucherStatus;
import com.codigo.demo.common.status.PromoCodeStatus;
import com.codigo.demo.domain.dto.EVoucherIssueReqDTO;
import com.codigo.demo.domain.dto.PromoDetailReqDTO;
import com.codigo.demo.domain.dto.PurchaseHistoryReqDTO;
import com.codigo.demo.domain.entity.EVoucherIssue;
import com.codigo.demo.domain.repository.EVoucherIssueRepository;
import com.codigo.demo.domain.repository.EVoucherRepository;
import com.codigo.demo.domain.service.EVoucherIssueService;
import com.codigo.demo.domain.service.PromoDetailService;
import com.codigo.demo.domain.service.PurchaseHistoryService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.modelmapper.GenericModelMapper;
import com.codigo.demo.utils.JsonUtils;

@Service
public class EVoucherIssueServiceImpl implements EVoucherIssueService {

	private final EVoucherIssueRepository eVoucherIssueRepository;

	private final EVoucherRepository eVoucherRepository;

	private final PromoDetailService promoDetailService;

	private final PurchaseHistoryService purchaseHistoryService;

	private Logger logger = LoggerFactory.getLogger(EVoucherIssueServiceImpl.class);

	private final GenericModelMapper mapper;

	public EVoucherIssueServiceImpl(final GenericModelMapper mapper,
			final EVoucherIssueRepository eVoucherIssueRepository, final EVoucherRepository eVoucherRepository,
			final PromoDetailService promoDetailService, final PurchaseHistoryService purchaseHistoryService) {
		this.eVoucherIssueRepository = eVoucherIssueRepository;
		this.mapper = mapper;
		this.eVoucherRepository = eVoucherRepository;
		this.promoDetailService = promoDetailService;
		this.purchaseHistoryService = purchaseHistoryService;
	}

	@Override
	public ServiceResult<Long> save(EVoucherIssueReqDTO dto) {
		ServiceResult<Long> result = new ServiceResult<>();
		try {
			EVoucherIssue eVoucherPurchase = new EVoucherIssue();
			var eVoucher = this.eVoucherRepository.findById(dto.getEVoucherId())
					.orElseThrow(() -> new ApiException("Fail to retrieve E-Voucher", "400",
							"No E-Voucher found for id " + dto.getEVoucherId()));
			logger.info("E-Voucher response : {}", JsonUtils.toJSON(eVoucher));

			// cannot buy if this e-voucher status is inactive or used and over max limit
			if (eVoucher.getStatus().equals(EVoucherStatus.INACTIVE) || eVoucher.getStatus().equals(EVoucherStatus.USED)
					|| (eVoucher.getQuantity() - eVoucher.getSoldQuantity()) <= dto.getPurchaseQuantity()) {
				throw new ApiException("Fail to buy E-Voucher", "400", "You cann't buy for this e-voucher ");
			}

			eVoucherPurchase = this.mapper.convertDTOToEntity(dto, EVoucherIssue.class);
			eVoucherPurchase.setId(null);
			eVoucherPurchase.setAmount(eVoucher.getAmount());
			eVoucherPurchase.setUsedQuantity(0);
			eVoucherPurchase = this.eVoucherIssueRepository.save(eVoucherPurchase);
			logger.info("E-Voucher purchase response : {}", JsonUtils.toJSON(eVoucherPurchase));

			// if all e-voucher is purchase then the e-vouhcer status is used
			if ((eVoucher.getQuantity() - eVoucher.getSoldQuantity()) == dto.getPurchaseQuantity()) {
				eVoucher.setStatus(EVoucherStatus.USED);
				this.eVoucherRepository.save(eVoucher);
			}

			eVoucher.setSoldQuantity(eVoucher.getSoldQuantity() + dto.getPurchaseQuantity());
			this.eVoucherRepository.save(eVoucher);

			result.success(eVoucherPurchase.getId());
		} catch (Exception e) {
			logger.info("Error occur in create e-voucher purchase : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	@Override
	public ServiceResult<Boolean> paidWithEvoucher(Long id, Double amount) {
		ServiceResult<Boolean> result = new ServiceResult<>();
		try {
			var eVoucherIssue = this.eVoucherIssueRepository.findById(id)
					.orElseThrow(() -> new ApiException("Fail to retrieve e-voucher issue ", "400",
							"No e-voucher issue found for id " + id));
			if (!EVoucherStatus.ACTIVE.equals(eVoucherIssue.getStatus())
					|| (eVoucherIssue.getPurchaseQuantity() - eVoucherIssue.getUsedQuantity() <= 0)) {
				throw new ApiException("Fail to paid with e-voucher ", "400", "Invalid e-voucher!");
			}

			if (eVoucherIssue.getAmount() < amount) {
				throw new ApiException("Fail to paid with e-voucher ", "400", "Invalid e-voucher amount!");
			}

			// is used all e-voucher set status to USED
			eVoucherIssue.setUsedQuantity(eVoucherIssue.getUsedQuantity() + 1);
			if (eVoucherIssue.getUsedQuantity() == eVoucherIssue.getPurchaseQuantity()) {
				eVoucherIssue.setStatus(EVoucherStatus.USED);
			}
			this.eVoucherIssueRepository.save(eVoucherIssue);

			// create promo code
			var promoDetail = getPormoDetailDTO(eVoucherIssue);
			var promoDetailSR = this.promoDetailService.save(promoDetail);

			// create purchase history 
			PurchaseHistoryReqDTO phDTO = new PurchaseHistoryReqDTO();
			phDTO.setEVoucherIssueId(eVoucherIssue.getId());
			phDTO.setPurchaseAmount(amount);
			phDTO.setPromoCode(promoDetailSR.getResult().getPromoCode());
			phDTO.setPurchaseAt(new Date());
			this.purchaseHistoryService.save(phDTO);
			result.success(Boolean.TRUE);
		} catch (Exception e) {
			logger.error("Error occur in paid with e-voucher : {}", e.getMessage());
			result.fail(e, e.getMessage());
		}
		return result;
	}

	public PromoDetailReqDTO getPormoDetailDTO(EVoucherIssue eVoucherIssue) {
		PromoDetailReqDTO dto = new PromoDetailReqDTO();
		dto.setAmount(getPormoAmount(eVoucherIssue.getAmount()));
		dto.setExpiryDate(eVoucherIssue.getEVoucher().getExpiryDate());
		dto.setStatus(PromoCodeStatus.ACTIVE);
		dto.setUserId(eVoucherIssue.getUserId());
		return dto;
	}

	/*
	 * eg of to get promo amount
	 */
	private Double getPormoAmount(Double amount) {
		Double promoAmount = 0.0;
		if (amount > 0 && amount <= 10) {
			promoAmount = 2.0;
		} else if (amount > 10 && amount < 20) {
			promoAmount = 4.0;
		} else {
			promoAmount = 4.0;
		}
		return promoAmount;
	}

}
