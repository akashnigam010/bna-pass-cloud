package in.bananaa.pass.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.bananaa.pass.dao.LoginDao;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.dto.user.LoginRequest;
import in.bananaa.pass.dto.user.LoginResponse;
import in.bananaa.pass.entity.User;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.security.JwtHelper;

@Service
public class LoginService {

	@Autowired
	private LoginDao dao;

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public LoginResponse login(LoginRequest request) throws BusinessException {
		Optional<User> optionalUser = dao.login(request);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			LoginResponse response = new LoginResponse();
			response.getUser().setId(user.getId());
			response.getUser().setName(user.getName());
			response.getUser().setLocation(user.getLocation());
			response.setAccessToken(getAccessToken(response));
			return response;
		} else {
			throw new BusinessException(GenericErrorCodeType.INCORRECT_LOGIN);
		}
	}

	private String getAccessToken(LoginResponse response) throws BusinessException {
		return JwtHelper.createJsonWebTokenForUser(response.getUser().getId().toString(), response.getUser().getName());
	}
}
