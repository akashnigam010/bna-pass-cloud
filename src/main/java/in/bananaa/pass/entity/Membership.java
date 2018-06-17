package in.bananaa.pass.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBERSHIP", schema = "bna", indexes = {
		@Index(name = "IDX_MEMBERSHIP_USER_MEMBER", columnList = "USER_ID, MEMBER_ID") })
public class Membership extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum Status {
		ACTIVE, BLOCKED;
	}

	public enum DayType {
		ALL_DAYS, WEEKDAYS, WEEKENDS;
	}

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID", nullable = false)
	private Member member;

	@Column(name = "STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DESCRIPTION_ID")
	private Description description;

	@Column(name = "DAY_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private DayType dayType;

	@Column(name = "ENTRIES_PER_DAY", nullable = false)
	private Integer entriesPerDay = 1;

	@Column(name = "START_DATE")
	private Calendar startDate;

	@Column(name = "END_DATE")
	private Calendar endDate;

	public Membership() {

	}

	public Membership(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
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

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
}
