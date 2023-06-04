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

import com.codigo.demo.common.status.PromoCodeStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "PROMO_DETAIL")
@Data
public class PromoDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "QR_CODE", columnDefinition = "TEXT")
	private String qrCode;

	@Column(name = "PROMO_CODE")
	private String promoCode;

	@Column(name = "AMOUNT", nullable = false)
	private Double amount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRY_DATE", nullable = false)
	private Date expiryDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private PromoCodeStatus status;

	@Column(name = "USER_ID")
	private Long userId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserMaster user;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT", nullable = false)
	private Date createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

}
