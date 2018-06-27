package in.bananaa.pass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.bananaa.pass.dto.IdRequest;
import in.bananaa.pass.dto.IdResponse;
import in.bananaa.pass.dto.PageRequest;
import in.bananaa.pass.dto.member.MemberRequest;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.member.MembershipResponse;
import in.bananaa.pass.dto.member.MembershipsResponse;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.service.MembershipService;
import in.bananaa.pass.validator.MembershipValidator;

@RestController
@CrossOrigin
@RequestMapping(value = "/membership")
public class MembershipController extends GenericController {

	@Autowired
	private MembershipService service;

	@Autowired
	private MembershipValidator validator;

	@RequestMapping(value = "/createOrUpdateMember", method = RequestMethod.POST, headers = HEADER)
	public IdResponse createOrUpdateMember(@RequestBody MemberRequest request) {
		try {
			validator.validate(request);
			return responseHelper.success(service.createOrUpdateMember(request));
		} catch (BusinessException e) {
			return responseHelper.failure(new IdResponse(), e);
		}
	}

	@RequestMapping(value = "/getMembership", method = RequestMethod.POST, headers = HEADER)
	public MembershipResponse getMembership(@RequestBody IdRequest request) {
		try {
			validator.validate(request);
			return responseHelper.success(service.getMembership(request));
		} catch (BusinessException e) {
			return responseHelper.failure(new MembershipResponse(), e);
		}
	}

	@RequestMapping(value = "/getMemberships", method = RequestMethod.POST, headers = HEADER)
	public MembershipsResponse getMemberships(@RequestBody PageRequest request) {
		try {
			validator.validate(request);
			return responseHelper.success(service.getMemberships(request));
		} catch (BusinessException e) {
			return responseHelper.failure(new MembershipsResponse(), e);
		}
	}

	@RequestMapping(value = "/createOrUpdateMembership", method = RequestMethod.POST, headers = HEADER)
	public IdResponse createOrUpdateMembership(@RequestBody MembershipRequest request) {
		try {
			validator.validate(request);
			return responseHelper.success(service.createOrUpdateMembership(request));
		} catch (BusinessException e) {
			return responseHelper.failure(new IdResponse(), e);
		}
	}
}
