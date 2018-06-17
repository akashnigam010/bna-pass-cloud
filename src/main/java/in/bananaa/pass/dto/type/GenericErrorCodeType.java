package in.bananaa.pass.dto.type;

import in.bananaa.pass.helper.BusinessErrorCode;

public enum GenericErrorCodeType implements BusinessErrorCode {
	REQUEST_VALIDATION_FAILED(90001),
	TOKEN_EXPIRED(90002),
	USER_NOT_LOGGED_IN(10003),
	GENERIC_ERROR(90004);

	private int value;

	GenericErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
