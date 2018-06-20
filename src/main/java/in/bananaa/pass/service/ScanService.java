package in.bananaa.pass.service;

import java.util.Calendar;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.bananaa.pass.dao.ScanDao;
import in.bananaa.pass.dto.scan.ScanRequest;
import in.bananaa.pass.dto.scan.ScanResponse;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.entity.Membership;
import in.bananaa.pass.entity.Membership.Status;
import in.bananaa.pass.helper.DateFormatType;
import in.bananaa.pass.helper.DateTimeUtil;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.mapper.ScanMapper;
import in.bananaa.pass.security.JwtTokenHelper;

@Service
public class ScanService {

	@Autowired
	private ScanDao dao;

	@Autowired
	private JwtTokenHelper tokenHelper;

	@Autowired
	private ScanMapper mapper;

	public ScanResponse verifyScan(ScanRequest request) throws BusinessException {
		ScanResponse response = new ScanResponse();
		Membership membership = null;
		Optional<Membership> optionalMembership = dao.getMembership(request, tokenHelper.getId());
		if (optionalMembership.isPresent()) {
			membership = optionalMembership.get();
			verifyAndCheckinMember(membership, response);
		} else {
			throw new BusinessException(GenericErrorCodeType.MEMBERSHIP_NOT_FOUND);
		}
		return mapper.map(membership, response);
	}

	private void verifyAndCheckinMember(Membership membership, ScanResponse response) {
		// 1. Check Status
		if (membership.getStatus() == Status.BLOCKED || membership.getStatus() == Status.SYSTEM_BLOCKED) {
			String reason = membership.getDescription().getDescription();
			if (StringUtils.isBlank(reason)) {
				reason = "Membership is blocked because of unknown reason";
			}
			response.setReason(reason);
			return;
		}

		// 2. Check Day
		if (!membership.getDayType().isAccessAllowed()) {
			response.setReason(membership.getDayType().getRejectionReason());
			return;
		}

		// 3. Check Date Range
		Calendar calendar = Calendar.getInstance();
		int startVal = calendar.compareTo(membership.getStartDate());
		int endVal = calendar.compareTo(membership.getEndDate());
		if (startVal < 0 && !DateUtils.isSameDay(calendar, membership.getStartDate())) {
			response.setReason("Membership has not started yet. It starts on "
					+ DateTimeUtil.formatDate(membership.getStartDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
			return;
		}

		if (endVal > 0 && !DateUtils.isSameDay(calendar, membership.getEndDate())) {
			response.setReason("Membership Expired on "
					+ DateTimeUtil.formatDate(membership.getEndDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
			return;
		}

		// Otherwise, access is allowed
		response.setAllowed(true);

		saveScanAsync(membership);

	}

	private void saveScanAsync(Membership membership) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				dao.saveScan(mapper.createScan(membership));
			}
		}).start();
	}
}
