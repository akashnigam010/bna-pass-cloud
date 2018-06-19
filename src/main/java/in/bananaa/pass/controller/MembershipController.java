package in.bananaa.pass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.bananaa.pass.dto.IdRequest;
import in.bananaa.pass.dto.StatusResponse;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.member.MembershipResponse;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.service.MembershipService;
import in.bananaa.pass.validator.MembershipValidator;

@RestController
@RequestMapping(value = "/membership")
public class MembershipController extends GenericController {

	@Autowired
	private MembershipService service;

	@Autowired
	private MembershipValidator validator;

	@RequestMapping(value = "/getMembership", method = RequestMethod.POST, headers = HEADER)
	public MembershipResponse getMembership(@RequestBody IdRequest request) {
		try {
			validator.validate(request);
			return responseHelper.success(service.getMembership(request));
		} catch (BusinessException e) {
			return responseHelper.failure(new MembershipResponse(), e);
		}
	}

	@RequestMapping(value = "/createOrUpdateMembership", method = RequestMethod.POST, headers = HEADER)
	public StatusResponse createOrUpdateMembership(@RequestBody MembershipRequest request) {
		StatusResponse response = new StatusResponse();
		try {
			validator.validate(request);
			service.createOrUpdateMembership(request);
			return responseHelper.success(response);
		} catch (BusinessException e) {
			return responseHelper.failure(response, e);
		}
	}
}