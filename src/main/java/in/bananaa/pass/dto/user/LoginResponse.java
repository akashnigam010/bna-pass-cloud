package in.bananaa.pass.dto.user;

import in.bananaa.pass.dto.GenericResponse;

public class LoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private User user;
	private String accessToken;

	public User getUser() {
		if (this.user == null) {
			this.user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
