package com.codigo.demo.common.type;

public enum BuyType {

	ONLY_ME_MESSAGE("Only me Usage"), GIFT_TO_OTHER("Gift to Others");

	private String typeVal;

	BuyType(String typeVal) {
		this.typeVal = typeVal;
	}

	@Override
	public String toString() {
		return typeVal;
	}

}
