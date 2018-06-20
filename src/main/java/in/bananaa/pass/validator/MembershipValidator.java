package in.bananaa.pass.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.bananaa.pass.dto.GenericRequest.RequestType;
import in.bananaa.pass.dto.IdRequest;
import in.bananaa.pass.dto.member.BlockMembershipRequest;
import in.bananaa.pass.dto.member.MemberRequest;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.dto.type.MembershipErrorCodeType;
import in.bananaa.pass.helper.exception.BusinessException;

@Component
public class MembershipValidator {

	public void validate(MemberRequest request) throws BusinessException {
		if (request.getType() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (StringUtils.isBlank(request.getFirstName())) {
			throw new BusinessException(MembershipErrorCodeType.FIRSTNAME_ERROR);
		}

		if (StringUtils.isBlank(request.getPhone())) {
			throw new BusinessException(MembershipErrorCodeType.PHONE_NUMBER_ERROR);
		}

		if (request.getType() == RequestType.UPDATE && request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validate(IdRequest request) throws BusinessException {
		if (request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validate(BlockMembershipRequest request) throws BusinessException {
		if (request.getId() == null || StringUtils.isBlank(request.getReason())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}

	public void validate(MembershipRequest request) throws BusinessException {
		if (request.getType() == null || request.getStatus() == null || request.getDayType() == null
				|| request.getEntriesPerDay() == null || StringUtils.isBlank(request.getStartDate())
				|| StringUtils.isBlank(request.getEndDate())) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}

		if (request.getMemberId() == null) {
			throw new BusinessException(MembershipErrorCodeType.MEMBER_ID_ERROR);
		}

		if (request.getType() == RequestType.UPDATE && request.getId() == null) {
			throw new BusinessException(GenericErrorCodeType.REQUEST_VALIDATION_FAILED);
		}
	}
}
