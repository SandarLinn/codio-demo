package com.codigo.demo.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.codigo.demo.common.status.EVoucherStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "E_VOUCHER")
@Data
public class EVoucher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "AMOUNT", nullable = false)
	private Double amount;

	@Column(name = "DESCRIPTION")
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE", nullable = false)
	private Date expiryDate;

	@Column(name = "QUANTITY", nullable = false, columnDefinition = "integer default 0")
	private Integer quantity;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private EVoucherStatus status;

	@Column(name = "SOLD_QUANTITY", columnDefinition = "integer default 0")
	private Integer soldQuantity;

	@Column(name = "DISCOUNT_PER_PAYMENT_ID")
	private Long discountPerPaymentId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "DISCOUNT_PER_PAYMENT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private DiscountPerPayment discountPerPayment;

	// define in BuyType.class
	@Column(name = "BUY_TYPE", nullable = false)
	private String buyType;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "GIFT_PER_LIMIT")
	private Long giftPerLimit;

	@Column(name = "MAX_LIMIT")
	private Long maxLimit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT", nullable = false)
	private Date createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

}
