package in.bananaa.pass.dto.member;

import java.io.Serializable;

public class BlockMembershipRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String reason;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
