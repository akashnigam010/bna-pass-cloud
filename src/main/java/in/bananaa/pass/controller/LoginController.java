package in.bananaa.pass.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.bananaa.pass.api.login.LoginRequest;
import in.bananaa.pass.api.login.StatusResponse;
import in.bananaa.pass.api.response.LoginResponse;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.security.JwtHelper;

@RestController
@RequestMapping(value = "/user")
public class LoginController extends GenericController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET, headers = HEADER)
	public StatusResponse hello() {
		return responseHelper.success(new StatusResponse());
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = HEADER)
	public LoginResponse login(@RequestBody LoginRequest request) {
		LoginResponse response = new LoginResponse();
		try {
			response.setId("123");
			response.setName("Seasons");
			response.setAccessToken(getAccessToken(response));
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
		return responseHelper.success(response);
	}

	private String getAccessToken(LoginResponse response) throws BusinessException {
		return JwtHelper.createJsonWebTokenForUser(response.getId(), response.getName());
	}
}
