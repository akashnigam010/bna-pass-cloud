package in.bananaa.pass.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.bananaa.pass.dto.scan.ScanRequest;
import in.bananaa.pass.dto.scan.ScanResponse;
import in.bananaa.pass.security.JwtTokenHelper;

@Service
public class ScanService {

	@Autowired
	private JwtTokenHelper tokenHelper;

	public ScanResponse verifyScan(ScanRequest request) {
		// TODO: add scan check logic
		return null;
	}
}
