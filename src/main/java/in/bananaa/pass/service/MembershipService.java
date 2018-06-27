package in.bananaa.pass.service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.bananaa.pass.dao.LoginDao;
import in.bananaa.pass.dao.MembershipDao;
import in.bananaa.pass.dto.GenericRequest.RequestType;
import in.bananaa.pass.dto.IdRequest;
import in.bananaa.pass.dto.PageRequest;
import in.bananaa.pass.dto.member.BlockMembershipRequest;
import in.bananaa.pass.dto.member.MemberRequest;
import in.bananaa.pass.dto.member.MemberResponse;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.member.MembershipResponse;
import in.bananaa.pass.dto.member.MembershipsResponse;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.entity.Member;
import in.bananaa.pass.entity.Membership;
import in.bananaa.pass.entity.Membership.Status;
import in.bananaa.pass.entity.User;
import in.bananaa.pass.helper.ScanCodeHelper;
import in.bananaa.pass.helper.exception.BusinessException;
import in.bananaa.pass.mapper.MemberMapper;
import in.bananaa.pass.security.JwtTokenHelper;

@Service
public class MembershipService {
	private static final Logger LOG = Logger.getLogger(MembershipService.class);

	@Autowired
	private MembershipDao dao;

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private JwtTokenHelper tokenHelper;

	@Autowired
	private ScanCodeHelper scanCodeHelper;

	@Autowired
	private MemberMapper mapper;

	public MembershipResponse getMembership(IdRequest request) throws BusinessException {
		Optional<Membership> optionalMembership = dao.getMembership(request.getId());
		if (optionalMembership.isPresent()) {
			return mapper.map(optionalMembership.get());
		} else {
			throw new BusinessException(GenericErrorCodeType.MEMBERSHIP_NOT_FOUND);
		}
	}

	public MembershipsResponse getMemberships(PageRequest request) throws BusinessException {
		MembershipsResponse response = new MembershipsResponse();
		Optional<List<Membership>> membershipsOptional = dao.getMemberships(request, tokenHelper.getId());
		if (membershipsOptional.isPresent()) {
			mapper.map(membershipsOptional.get(), response);
		}
		return response;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void blockMembership(BlockMembershipRequest request) throws BusinessException {
		Optional<Membership> optionalMembership = dao.getMembership(request, tokenHelper.getId());
		if (optionalMembership.isPresent()) {
			Calendar calendar = Calendar.getInstance();
			Membership membership = optionalMembership.get();
			membership.setStatus(Status.BLOCKED);
			membership.getDescription().setDescription(request.getReason());
			membership.getDescription().setUpdatedDateTime(calendar);
			membership.setUpdatedDateTime(calendar);
			dao.saveMembership(membership);
		} else {
			throw new BusinessException(GenericErrorCodeType.MEMBERSHIP_NOT_FOUND);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MemberResponse createOrUpdateMember(MemberRequest request) throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		Member member = null;
		if (request.getType() == RequestType.NEW) {
			// TODO : check using phone if the user already exists in db, so that creating a
			// new
			// membership doesn't create a new user every time
			member = new Member(calendar, calendar);
			mapAndSaveMember(request, member);
		} else if (request.getType() == RequestType.UPDATE) {
			Optional<Member> optionalMember = dao.getMember(request.getId());
			if (optionalMember.isPresent()) {
				member = optionalMember.get();
				mapAndSaveMember(request, member);
			} else {
				LOG.error("Error occurred while trying to update membership without a member. User Id : "
						+ tokenHelper.getId() + ", Member Id : " + request.getId());
				throw new BusinessException(GenericErrorCodeType.USER_NOT_FOUND);
			}
		} else {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
		return mapper.mapCreateMemberResponse(member);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void createOrUpdateMembership(MembershipRequest request) throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		Optional<User> optionalUser = loginDao.getUser(tokenHelper.getId());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<Member> optionalMember = dao.getMember(request.getMemberId());
			if (optionalMember.isPresent()) {
				Member member = optionalMember.get();
				Membership membership = null;
				if (request.getType() == RequestType.NEW) {
					Optional<Membership> optionalMembership = dao.getMembership(user.getId(), member.getId());
					if (optionalMembership.isPresent()) {
						// this means that user initiated a new membership, when already a membership
						// exists - update the same then
						membership = optionalMembership.get();
					} else {
						membership = new Membership(calendar, calendar);
						// set scan code only when membership is being created the very first time
						membership.setScanCode(scanCodeHelper.generateCode(member.getId(), user.getId()));
					}
				} else if (request.getType() == RequestType.UPDATE) {
					Optional<Membership> optionalMembership = dao.getMembership(request.getId());
					if (optionalMembership.isPresent()) {
						membership = optionalMembership.get();
						if (!membership.getMember().equals(member)) {
							LOG.error(
									"Error occurred:  Mismatch in memberId between UpdateMembershipRequest and Membership. User Id : "
											+ tokenHelper.getId() + ", Membership Id : " + membership.getId());
							throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
						}
					} else {
						LOG.error("Error occurred while trying to update membership without a membership. User Id : "
								+ tokenHelper.getId() + ", Membership Id : " + request.getId());
						throw new BusinessException(GenericErrorCodeType.MEMBERSHIP_NOT_FOUND);
					}
				} else {
					LOG.error("Error occurred: Request Type Invalid. User Id : " + tokenHelper.getId());
					throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
				}

				mapAndSaveMembership(request, membership, member, user);
			} else {
				LOG.error("Error occurred while trying to create/update membership without member. User Id : "
						+ tokenHelper.getId());
				throw new BusinessException(GenericErrorCodeType.MEMBER_NOT_FOUND);
			}
		} else {
			LOG.error("Error occurred while trying to create/update membership without user. User Id : "
					+ tokenHelper.getId());
			throw new BusinessException(GenericErrorCodeType.USER_NOT_FOUND);
		}

	}

	private void mapAndSaveMember(MemberRequest request, Member member) {
		mapper.map(request, member);
		dao.saveMember(member);
	}

	private void mapAndSaveMembership(MembershipRequest request, Membership membership, Member member, User user)
			throws BusinessException {
		mapper.map(request, membership, member, user);
		dao.saveMembership(membership);
	}
}
