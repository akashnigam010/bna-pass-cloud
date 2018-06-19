package in.bananaa.pass.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.bananaa.pass.dto.GenericRequest.RequestType;
import in.bananaa.pass.dto.IdRequest;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.helper.exception.BusinessException;

@Component
public class MembershipValidator {

	public void validate(IdRequest request) throws BusinessException {
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validate(MembershipRequest request) throws BusinessException {
		if (request.getType() == null || request.getStatus() == null || request.getDayType() == null
				|| request.getEntriesPerDay() == null || StringUtils.isBlank(request.getStartDate())
				|| StringUtils.isBlank(request.getEndDate())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getMember() == null || StringUtils.isBlank(request.getMember().getFirstName())
				|| StringUtils.isBlank(request.getMember().getPhone())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getType() == RequestType.UPDATE
				&& (request.getId() == null || request.getMember().getId() == null)) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
