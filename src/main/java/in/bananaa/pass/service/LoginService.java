package in.bananaa.pass.service;

import org.springframework.stereotype.Service;

import in.bananaa.pass.dto.user.LoginRequest;
import in.bananaa.pass.dto.user.LoginResponse;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.security.JwtHelper;

@Service
public class LoginService {

	public LoginResponse login(LoginRequest request) throws BusinessException {
		LoginResponse response = new LoginResponse();
		response.setId("123");
		response.setName("Seasons");
		response.setAccessToken(getAccessToken(response));
		return response;
	}
	
	private String getAccessToken(LoginResponse response) throws BusinessException {
		return JwtHelper.createJsonWebTokenForUser(response.getId(), response.getName());
	}
}
