package in.bananaa.pass.security;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();

		// User parsedUser = jwtUtil.parseToken(token);
		TokenInfo tokenInfo = JwtHelper.verifyToken(token);

		if (tokenInfo == null) {
			// throw new JwtTokenMalformedException("JWT token is not valid");
			throw new RuntimeException("JWT token is not valid");
		}

		List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(tokenInfo.getRole());

		return new AuthenticatedUser(tokenInfo.getId(), tokenInfo.getName(), tokenInfo.getDeviceId(), token,
				authorityList);
	}
}