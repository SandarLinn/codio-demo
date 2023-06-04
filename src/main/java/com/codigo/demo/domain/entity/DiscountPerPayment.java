package com.codigo.demo.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.codigo.demo.common.type.PaymentMethod;

import lombok.Data;

@Entity
@Table(name = "DISCOUNT_PER_PAYMENT")
@Data
public class DiscountPerPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "PAYMENT_METHOD", nullable = false)
	private PaymentMethod paymentMethod;

	@Column(name = "DISCOUNT_PERCENT", nullable = false)
	private Double discountPercent;

	@Column(name = "DISCOUNT_AMOUNT")
	private Double discountAmount;

}
