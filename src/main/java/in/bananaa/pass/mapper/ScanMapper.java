package in.bananaa.pass.mapper;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import in.bananaa.pass.dto.scan.ScanResponse;
import in.bananaa.pass.entity.Member;
import in.bananaa.pass.entity.Membership;
import in.bananaa.pass.entity.Scan;
import in.bananaa.pass.helper.DateFormatType;
import in.bananaa.pass.helper.DateTimeUtil;

@Component
public class ScanMapper {

	public ScanResponse map(Membership membership, ScanResponse response) {
		Member member = membership.getMember();
		response.getMember().setId(member.getId());
		response.getMember().setFirstName(member.getFirstName());
		response.getMember().setLastName(member.getLastName());
		response.setStartDate(
				DateTimeUtil.formatDate(membership.getStartDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		response.setEndDate(DateTimeUtil.formatDate(membership.getEndDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		return response;
	}

	public Scan createScan(Membership membership) {
		Scan scan = new Scan(Calendar.getInstance());
		scan.setUser(membership.getUser());
		scan.setMember(membership.getMember());
		return scan;
	}
}
