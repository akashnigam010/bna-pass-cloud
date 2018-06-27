package in.bananaa.pass.dto.member;

import in.bananaa.pass.dto.GenericResponse;

public class MembershipResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private MembershipDto membership;

	public MembershipDto getMembership() {
		if (this.membership == null) {
			this.membership = new MembershipDto();
		}
		return membership;
	}

	public void setMembership(MembershipDto membership) {
		this.membership = membership;
	}
}
