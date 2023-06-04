package com.codigo.demo.domain.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codigo.demo.common.ServiceResult;
import com.codigo.demo.common.status.EVoucherStatus;
import com.codigo.demo.domain.dto.EVoucherIssueReqDTO;
import com.codigo.demo.domain.entity.EVoucherIssue;
import com.codigo.demo.domain.repository.EVoucherIssueRepository;
import com.codigo.demo.domain.repository.EVoucherRepository;
import com.codigo.demo.domain.service.EVoucherIssueService;
import com.codigo.demo.exception.ApiException;
import com.codigo.demo.modelmapper.GenericModelMapper;
import com.codigo.demo.utils.JsonUtils;

@Service
public class EVoucherIssueServiceImpl implements EVoucherIssueService {

	private final EVoucherIssueRepository eVoucherIssueRepository;

	private final EVoucherRepository eVoucherRepository;

	private Logger logger = LoggerFactory.getLogger(EVoucherIssueServiceImpl.class);

	private final GenericModelMapper mapper;

	public EVoucherIssueServiceImpl(final GenericModelMapper mapper,
			final EVoucherIssueRepository eVoucherIssueRepository, final EVoucherRepository eVoucherRepository) {
		this.eVoucherIssueRepository = eVoucherIssueRepository;
		this.mapper = mapper;
		this.eVoucherRepository = eVoucherRepository;
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

}
