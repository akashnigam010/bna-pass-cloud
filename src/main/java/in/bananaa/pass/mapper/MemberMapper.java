package in.bananaa.pass.mapper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import in.bananaa.pass.dto.member.MemberRequest;
import in.bananaa.pass.dto.member.MemberResponse;
import in.bananaa.pass.dto.member.MembershipDto;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.member.MembershipResponse;
import in.bananaa.pass.dto.member.MembershipsResponse;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.entity.Member;
import in.bananaa.pass.entity.Membership;
import in.bananaa.pass.entity.User;
import in.bananaa.pass.helper.DateFormatType;
import in.bananaa.pass.helper.DateTimeUtil;
import in.bananaa.pass.helper.exception.BusinessException;

@Component
public class MemberMapper {

	public Member map(MemberRequest request, Member member) {
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

	public MemberRequest map(Member member) {
		MemberRequest memberDto = new MemberRequest();
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
		Calendar calendar = Calendar.getInstance();
		membership.setMember(member);
		membership.setUser(user);
		membership.setStatus(request.getStatus());
		membership.setDayType(request.getDayType());
		membership.setEntriesPerDay(request.getEntriesPerDay());
		if (StringUtils.isNotBlank(request.getDescription())) {
			membership.getDescription().setDescription(request.getDescription());
			membership.getDescription().setUpdatedDateTime(calendar);
		}

		try {
			membership.setStartDate(
					DateTimeUtil.parseCalendar(request.getStartDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
			membership.setEndDate(
					DateTimeUtil.parseCalendar(request.getEndDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		} catch (ParseException e) {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		membership.setUpdatedDateTime(calendar);
		return membership;
	}

	public MembershipResponse map(Membership membership) {
		MembershipResponse response = new MembershipResponse();
		MembershipDto membershipDto = response.getMembership();
		map(membership, membershipDto);
		return response;
	}

	public MemberResponse mapCreateMemberResponse(Member member) {
		MemberResponse response = new MemberResponse();
		response.setId(member.getId());
		response.setFirstName(member.getFirstName());
		response.setLastName(member.getLastName());
		response.setImageUrl(member.getImageUrl());
		return response;
	}

	public void map(List<Membership> list, MembershipsResponse response) {
		list.forEach(membership -> {
			MembershipDto membershipDto = new MembershipDto();
			map(membership, membershipDto);
			response.getMemberships().add(membershipDto);
		});
	}

	private void map(Membership membership, MembershipDto dto) {
		dto.setId(membership.getId());
		dto.setMember(map(membership.getMember()));
		dto.setStatus(membership.getStatus());
		if (StringUtils.isNotBlank(membership.getDescription().getDescription())) {
			dto.setDescription(membership.getDescription().getDescription());
		}
		dto.setDayType(membership.getDayType());
		dto.setEntriesPerDay(membership.getEntriesPerDay());
		dto.setStartDate(DateTimeUtil.formatDate(membership.getStartDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
		dto.setEndDate(DateTimeUtil.formatDate(membership.getEndDate(), DateFormatType.DATE_FORMAT_DD_MM_YYYY));
	}
}
