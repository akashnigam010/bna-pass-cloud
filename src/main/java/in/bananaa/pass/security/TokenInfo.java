package in.bananaa.pass.security;

import org.joda.time.DateTime;

public class TokenInfo {
	public static final String INFO = "info";
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String ROLE = "role";
	public static final String DEVICE_ID = "deviceId";
	public static final String ISSUED_AT = "issuedAt";
	public static final String EXPIRES_AT = "expiresAt";

	private String id;
	private String name;
	private String role;
	private String deviceId;
	private DateTime issuedAt;
	private DateTime expiresAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getIssuedAt() {
		return issuedAt;
	}

	public void setIssuedAt(DateTime issuedAt) {
		this.issuedAt = issuedAt;
	}

	public DateTime getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(DateTime expiresAt) {
		this.expiresAt = expiresAt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}