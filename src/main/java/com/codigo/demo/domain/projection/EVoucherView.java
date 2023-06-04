package com.codigo.demo.domain.projection;

import java.util.Date;

public interface EVoucherView {
	Long getId();
	String getbuyType();
	Date getcreatedAt();
	String getdescription();
	Date getexpiryDate();
	Integer getgitfPerLimit();
	Integer getMaxLimit();
	String getName();
	String getPhone();
	Integer getquantity();
	Integer getsoldQuantity();
	String getStatus();
	String getTitle();
	Long getdiscountPerPaymentId();
	String getpaymentMethod();
	Double getDiscountPercent();
	Double getDiscountAmount();
}
