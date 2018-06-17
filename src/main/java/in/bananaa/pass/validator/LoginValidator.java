package in.bananaa.pass.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.dto.user.LoginRequest;
import in.bananaa.pass.helper.exception.BusinessException;

@Component
public class LoginValidator {

	public void validate(LoginRequest request) throws BusinessException {
		if (request.getId() == null || StringUtils.isBlank(request.getPassword())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
