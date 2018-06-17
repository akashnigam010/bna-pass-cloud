package in.bananaa.pass.dto.user;

import in.bananaa.pass.dto.GenericResponse;

public class LoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String location;
	private String accessToken;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}