package in.bananaa.pass.api.response;

public class LoginResponse extends GenericResponse {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
