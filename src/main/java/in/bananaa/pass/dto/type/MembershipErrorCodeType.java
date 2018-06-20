package in.bananaa.pass.dto.type;

import in.bananaa.pass.helper.BusinessErrorCode;

public enum MembershipErrorCodeType implements BusinessErrorCode {
	FIRSTNAME_ERROR(10001),

	PHONE_NUMBER_ERROR(10002),
	
	MEMBER_ID_ERROR(10003);

	private int value;

	MembershipErrorCodeType(int value) {
		this.value = value;
	}

	public Integer getBusinessErrorCode() {
		return value;
	}
}
