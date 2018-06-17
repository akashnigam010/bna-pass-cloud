package in.bananaa.pass.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.bananaa.pass.dto.scan.ScanRequest;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.helper.exception.BusinessException;

@Component
public class ScanValidator {

	public void validate(ScanRequest request) throws BusinessException {
		if (StringUtils.isBlank(request.getCode())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
