package in.bananaa.pass.dto.scan;

import in.bananaa.pass.dto.GenericResponse;
import in.bananaa.pass.dto.member.MemberResponse;

public class ScanResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private MemberResponse member;
	private boolean isValid;
	private String reason;

	public MemberResponse getMember() {
		return member;
	}

	public void setMember(MemberResponse member) {
		this.member = member;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
