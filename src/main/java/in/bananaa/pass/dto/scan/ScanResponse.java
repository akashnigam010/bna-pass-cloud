package in.bananaa.pass.dto.scan;

import in.bananaa.pass.dto.GenericResponse;
import in.bananaa.pass.dto.member.MemberResponse;

public class ScanResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private MemberResponse member;
	private boolean allowed = false;
	private String reason;
	private String startDate;
	private String endDate;

	public MemberResponse getMember() {
		if (this.member == null) {
			this.member = new MemberResponse();
		}
		return member;
	}

	public void setMember(MemberResponse member) {
		this.member = member;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean isAllowed) {
		this.allowed = isAllowed;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
