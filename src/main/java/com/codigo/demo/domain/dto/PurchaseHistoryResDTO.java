package com.codigo.demo.domain.dto;

import java.util.Date;

import com.codigo.demo.domain.entity.EVoucherIssue;

import lombok.Data;

@Data
public class PurchaseHistoryResDTO {
	private Long id;

	private Long eVoucherPurchaseId;

	private EVoucherIssue eVoucherIssue;

	private Double purchaseAmount;

	private Date purchaseAt;

	private String description;

	private String promoCode;
}
