package in.bananaa.pass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.bananaa.pass.dto.user.LoginRequest;
import in.bananaa.pass.dto.user.LoginResponse;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.service.LoginService;
import in.bananaa.pass.validator.LoginValidator;

@RestController
@RequestMapping(value = "/user")
public class LoginController extends GenericController {

	@Autowired
	private LoginService service;

	@Autowired
	private LoginValidator validator;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = HEADER)
	public LoginResponse login(@RequestBody LoginRequest request) {
		try {
			validator.validate(request);
			return responseHelper.success(service.login(request));
		} catch (BusinessException e) {
			return responseHelper.failure(new LoginResponse(), e);
		}
	}
}
