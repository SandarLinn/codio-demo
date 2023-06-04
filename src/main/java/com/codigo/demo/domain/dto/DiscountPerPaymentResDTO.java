package com.codigo.demo.domain.dto;

import com.codigo.demo.common.type.PaymentMethod;

import lombok.Data;

@Data
public class DiscountPerPaymentResDTO {

	private Long id;

	private PaymentMethod paymentMethod;

	private Double discountPercent;

	private Double discountAmount;
}
