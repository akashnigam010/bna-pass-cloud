package in.bananaa.pass.dto.type;

import in.bananaa.pass.helper.BusinessErrorCode;

public enum GenericErrorCodeType implements BusinessErrorCode {
	GENERIC_ERROR(90001),

	TOKEN_EXPIRED(90002),

	REQUEST_VALIDATION_FAILED(90003),

	USER_NOT_LOGGED_IN(90004),

	USER_NOT_FOUND(90005),
	
	MEMBER_NOT_FOUND(90006),
	
	MEMBERSHIP_NOT_FOUND(90007);

	private int value;

	GenericErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
