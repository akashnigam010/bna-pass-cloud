package in.bananaa.pass.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.bananaa.pass.api.login.StatusResponse;

@RestController
@RequestMapping(value = "/search")
public class SearchController extends GenericController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET, headers = HEADER)
	public StatusResponse hello() {
		return responseHelper.success(new StatusResponse());
	}
}
