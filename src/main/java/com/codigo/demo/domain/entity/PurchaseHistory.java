package com.codigo.demo.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "PURCHASE_HISTORY")
@Data
public class PurchaseHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "E_VOUCHER_ISSUE_ID", nullable = false)
	private Long eVoucherPurchaseId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "E_VOUCHER_ISSUE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private EVoucherIssue eVoucherIssue;

	@Column(name = "PURCHASE_AMOUNT", nullable = false)
	private Double purchaseAmount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PURCHASE_AT", nullable = false)
	private Date purchaseAt;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PROMO_CODE")
	private String promoCode;

}
