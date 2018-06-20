package in.bananaa.pass.entity;

import static java.util.Calendar.*;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBERSHIP", schema = "bna", indexes = {
		@Index(name = "IDX_UNQ_USER_MEMBER", columnList = "USER_ID, MEMBER_ID"),
		@Index(name = "IDX_UNQ_SCAN_CODE", columnList = "SCAN_CODE") })
public class Membership extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String EMPTY_STRING = "";

	public enum Status {
		ACTIVE, BLOCKED, SYSTEM_BLOCKED;
	}

	public enum DayType {
		ALL_DAYS, WEEKDAYS, WEEKENDS;

		public boolean isAccessAllowed() {
			final Calendar c = Calendar.getInstance();
			int day = c.get(DAY_OF_WEEK);
			if (this == ALL_DAYS) {
				return true;
			} else if (this == WEEKDAYS) {
				if (day == MONDAY || day == TUESDAY || day == WEDNESDAY || day == THURSDAY || day == FRIDAY) {
					return true;
				}
			} else {
				if (day == SATURDAY || day == SUNDAY) {
					return true;
				}
			}
			return false;
		}
		
		public String getRejectionReason() {
			if (this == ALL_DAYS) {
				return EMPTY_STRING;
			} else if (this == WEEKDAYS) {
				return "Membership access only for Weekdays";
			} else if (this == WEEKENDS) {
				return "Membership access only for Weekends";
			}
			return EMPTY_STRING;
		}
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
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

	@Column(name = "START_DATE", nullable = false)
	private Calendar startDate;

	@Column(name = "END_DATE", nullable = false)
	private Calendar endDate;

	@Column(name = "SCAN_CODE", nullable = false)
	private String scanCode;

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
		if (this.description == null) {
			Calendar calendar = Calendar.getInstance();
			this.description = new Description(calendar, calendar);
		}
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

	public String getScanCode() {
		return scanCode;
	}

	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}
}
