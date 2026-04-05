package com.api.constant;

public enum Warranty_status {

	IN_WARRANTY(1), OUT_WARRANTY(2);

	int code;

	private Warranty_status(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
