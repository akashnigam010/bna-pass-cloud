package in.bananaa.pass.dto.member;

import in.bananaa.pass.dto.GenericRequest;
import in.bananaa.pass.entity.Membership.DayType;
import in.bananaa.pass.entity.Membership.Status;

public class MembershipRequest extends GenericRequest {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer memberId;
	private Status status;
	private String description;
	private DayType dayType;
	private Integer entriesPerDay;
	private String startDate;
	private String endDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DayType getDayType() {
		return dayType;
	}

	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

	public Integer getEntriesPerDay() {
		return entriesPerDay;
	}

	public void setEntriesPerDay(Integer entriesPerDay) {
		this.entriesPerDay = entriesPerDay;
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
