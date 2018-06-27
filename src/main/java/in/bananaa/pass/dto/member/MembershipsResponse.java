package in.bananaa.pass.dto.member;

import java.util.ArrayList;
import java.util.List;

import in.bananaa.pass.dto.GenericResponse;

public class MembershipsResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private List<MembershipDto> memberships;

	public List<MembershipDto> getMemberships() {
		if (this.memberships == null) {
			this.memberships = new ArrayList<>();
		}
		return memberships;
	}

	public void setMemberships(List<MembershipDto> memberships) {
		this.memberships = memberships;
	}
}
