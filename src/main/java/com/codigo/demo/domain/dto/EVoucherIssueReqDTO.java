package com.codigo.demo.domain.dto;

import com.codigo.demo.common.status.EVoucherStatus;

import lombok.Data;

@Data
public class EVoucherIssueReqDTO {

	private Long id;

	private Long userId;

	private Long eVoucherId;

	private Integer purchaseQuantity;

	private EVoucherStatus status;

	private String name;

	private String phone;

}
