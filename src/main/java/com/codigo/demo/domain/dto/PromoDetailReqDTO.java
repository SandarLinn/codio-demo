package com.codigo.demo.domain.dto;

import java.util.Date;

import com.codigo.demo.common.status.PromoCodeStatus;

import lombok.Data;

@Data
public class PromoDetailReqDTO {

	private Long id;

	private Double amount;

	private Date expiryDate;

	private PromoCodeStatus status;

	private Long userId;

}
