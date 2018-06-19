package in.bananaa.pass.mapper;

import java.text.ParseException;
import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.bananaa.pass.dto.member.MemberDto;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.member.MembershipResponse;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.entity.Description;
import in.bananaa.pass.entity.Member;
import in.bananaa.pass.entity.Membership;
import in.bananaa.pass.entity.User;
import in.bananaa.pass.helper.DateFormatType;
import in.bananaa.pass.helper.DateTimeUtil;
import in.bananaa.pass.helper.exception.BusinessException;

@Component
public class MemberMapper {

	public Member map(MemberDto request, Member member) {
		member.setFirstName(request.getFirstName());
		member.setLastName(request.getLastName());
		member.setImageUrl(request.getImageUrl());
		member.setPhone(request.getPhone());
		member.setEmail(request.getEmail());
		member.setAddress(request.getAddress());
		member.setIdProof(request.getIdProof());
		member.setUpdatedDateTime(Calendar.getInstance());
		return member;
	}

	public MemberDto map(Member member) {
		MemberDto memberDto = new MemberDto();
		memberDto.setId(member.getId());
		memberDto.setFirstName(member.getFirstName());
		memberDto.setLastName(member.getLastName());
		memberDto.setImageUrl(member.getImageUrl());
		memberDto.setPhone(member.getPhone());
		memberDto.setEmail(member.getEmail());
		memberDto.setAddress(member.getAddress());
		memberDto.setIdProof(member.getIdProof());
		return memberDto;
	}

	public Membership map(MembershipRequest request, Membership membership, Member member, User user)
			throws BusinessException {
		membership.setMember(member);
		membership.setUser(user);
		membership.setStatus(request.getStatus());
		membership.setDayType(request.getDayType());
		membership.setEntriesPerDay(request.getEntriesPerDay());
		if (StringUtils.isNotBlank(request.getDescription())) {
			Calendar calendar = Calendar.getInstance();
			Description description = new Description(calendar, calendar);
			description.setDescription(request.getDescription());
			membership.setDescription(description);
		}

		try {
			membership.setStartDate(
					DateTimeUtil.parseCalendar(request.getStartDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
			membership.setEndDate(
					DateTimeUtil.parseCalendar(request.getEndDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		} catch (ParseException e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		membership.setUpdatedDateTime(Calendar.getInstance());
		return membership;
	}

	public MembershipResponse map(Membership membership) {
		MembershipResponse response = new MembershipResponse();
		response.setId(membership.getId());
		response.setMember(map(membership.getMember()));
		response.setStatus(membership.getStatus());
		if (membership.getDescription() != null) {
			response.setDescription(membership.getDescription().getDescription());
		}
		response.setDayType(membership.getDayType());
		response.setEntriesPerDay(membership.getEntriesPerDay());
		response.setStartDate(
				DateTimeUtil.formatDate(membership.getStartDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		response.setEndDate(DateTimeUtil.formatDate(membership.getEndDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		return response;
	}
}
