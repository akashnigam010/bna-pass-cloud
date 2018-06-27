package in.bananaa.pass.security;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationFilter(String defaultProcessingUrl) {
		super(defaultProcessingUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
			response.setStatus(200);
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET, POST");
			response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With");
			return null;
		}

		String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new AuthenticationCredentialsNotFoundException("No JWT token found in request headers");
		}

		String authToken = header.substring(7);
		TokenInfo user = JwtHelper.verifyToken(authToken);
		GrantedAuthority role = new RoleGrantedAuthority(user.getRole());
		JwtAuthenticationToken authRequest = new JwtAuthenticationToken(user.getId(), user.getId(),
				Arrays.asList(role));
		authRequest.setToken(authToken);

		return getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		// As this authentication is in HTTP header, after success we need to
		// continue the request normally
		// and return the response as if the resource was not secured at all
		chain.doFilter(request, response);
	}
}