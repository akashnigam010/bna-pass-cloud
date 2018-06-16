package in.bananaa.pass.security;

import in.bananaa.pass.helper.exception.BusinessException;

public interface JwtTokenHelper {
	public boolean isUserLoggedIn();
	public Integer getId() throws BusinessException;
	public String getName() throws BusinessException;
	public void setAuthUser(String jwtToken);
}
