package in.bananaa.pass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.bananaa.pass.dto.scan.ScanRequest;
import in.bananaa.pass.dto.scan.ScanResponse;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.service.ScanService;
import in.bananaa.pass.validator.ScanValidator;

@RestController
@RequestMapping(value = "/scan")
public class ScanController extends GenericController {

	@Autowired
	private ScanService service;

	@Autowired
	private ScanValidator validator;

	@RequestMapping(value = "/check", method = RequestMethod.POST, headers = HEADER)
	public ScanResponse checkUser(@RequestBody ScanRequest request) {
		try {
			validator.validate(request);
			return responseHelper.success(service.verifyScan(request));
		} catch (BusinessException e) {
			return responseHelper.failure(new ScanResponse(), e);
		}
	}
}
