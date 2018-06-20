package in.bananaa.pass.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "MEMBER", schema = "bna")
public class Member extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "PHONE", nullable = false)
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ID_PROOF")
	private String idProof;

	@Column(name = "ADDRESS")
	private String address;

	public Member() {

	}

	public Member(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return StringUtils.isNotBlank(this.lastName) ? this.lastName : "";
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdProof() {
		return idProof;
	}

	public void setIdProof(String idProof) {
		this.idProof = idProof;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return StringUtils.strip(this.getFirstName() + " " + this.getLastName());
	}
}
