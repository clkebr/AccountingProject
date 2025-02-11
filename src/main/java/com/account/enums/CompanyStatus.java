package com.account.enums;

public enum CompanyStatus {

	ACTIVE("Active"), PASSIVE("Passive");

	private final String value;

	CompanyStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
