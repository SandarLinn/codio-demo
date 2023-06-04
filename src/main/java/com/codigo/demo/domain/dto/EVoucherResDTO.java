package com.codigo.demo.domain.dto;

import java.util.Date;

import com.codigo.demo.common.status.EVoucherStatus;

import lombok.Data;

@Data
public class EVoucherResDTO {

	private Long id;

	private String title;

	private String description;

	private Date expiryDate;

	private Integer quantity;

	private EVoucherStatus status;

	private Integer soldQuantity;

	// id of discountPerPayment table
	private Long discountPerPaymentId;

	//define in BuyType.class
	private String buyType;

	private String name;

	private String phone;

	private Long giftPerLimit;

	private Long maxLimit;

}
