package in.bananaa.pass.security;

import org.springframework.security.core.GrantedAuthority;

public class RoleGrantedAuthority implements GrantedAuthority {
	String role;

	private static final long serialVersionUID = 1L;

	public RoleGrantedAuthority(String role) {
		super();
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}

}
