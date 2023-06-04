package com.codigo.demo.domain.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PurchaseHistoryReqDTO {

	private Long id;

	private Long eVoucherIssueId;

	private Double purchaseAmount;

	private Date purchaseAt;

	private String description;
	
	private String promoCode;

}
