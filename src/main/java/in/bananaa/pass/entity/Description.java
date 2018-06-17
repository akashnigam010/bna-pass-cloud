package in.bananaa.pass.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DESCRIPTION", schema = "bna")
public class Description extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	public Description() {

	}

	public Description(Calendar createdDateTime, Calendar updatedDateTime) {
		super(createdDateTime, updatedDateTime);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
