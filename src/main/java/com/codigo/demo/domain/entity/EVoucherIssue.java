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
@Table(name = "E_VOUCHER_ISSUE")
@Data
public class EVoucherIssue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserMaster user;

	@Column(name = "E_VOUCHER_ID", nullable = false)
	private Long eVoucherId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "E_VOUCHER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private EVoucher eVoucher;

	@Column(name = "PURCHASE_QUANTITY", nullable = false, columnDefinition = "integer default 0")
	private Integer purchaseQuantity;

	@Column(name = "USED_QUANTITY", columnDefinition = "integer default 0")
	private Integer usedQuantity;

	@Column(name = "AMOUNT")
	private Double amount;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private EVoucherStatus status;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PHONE")
	private String phone;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT", nullable = false)
	private Date createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

}
