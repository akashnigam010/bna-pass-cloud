package in.bananaa.pass.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in.bananaa.pass.api.type.GenericErrorCodeType;
import in.bananaa.pass.api.type.RoleType;
import in.bananaa.pass.helper.exception.BusinessException;

@Service
public class JwtTokenHelperImpl implements JwtTokenHelper {
	private static final String ANONYMOUS_USER = "anonymousUser";

	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	public boolean isUserLoggedIn() {
		for (String role : getRoles()) {
			RoleType roleType = RoleType.getRole(role);
			if (RoleType.USER == roleType) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}

	@Override
	public Integer getId() throws BusinessException {
		if (!isUserLoggedIn()) {
			throw new BusinessException(GenericErrorCodeType.USER_NOT_LOGGED_IN);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
		return Integer.valueOf(userDetails.getId());
	}
	
	@Override
	public String getName() throws BusinessException {
		if (!isUserLoggedIn()) {
			throw new BusinessException(GenericErrorCodeType.USER_NOT_LOGGED_IN);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
		return userDetails.getName();
	}

	@Override
	public void setAuthUser(String authToken) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (!StringUtils.isEmpty(authToken)) {
			TokenInfo user = JwtHelper.verifyToken(authToken);
			GrantedAuthority role = new RoleGrantedAuthority(user.getRole());
			JwtAuthenticationToken authRequest = new JwtAuthenticationToken(user.getId(), user.getId(),
					Arrays.asList(role));
			authRequest.setToken(authToken);

			Authentication authentication = authenticationManager.authenticate(authRequest);
			securityContext.setAuthentication(authentication);
		} else {
			securityContext.getAuthentication().setAuthenticated(false);
		}
	}

	@SuppressWarnings("unchecked")
	private List<String> getRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication.getPrincipal() == ANONYMOUS_USER) {
			return Collections.EMPTY_LIST;
		}
		AuthenticatedUser userDetails = (AuthenticatedUser) authentication.getPrincipal();
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) userDetails.getAuthorities();
		if (authorities == null || authorities.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority authority : authorities) {
			roles.add(authority.getAuthority());
		}
		return roles;
	}
}
