package in.bananaa.pass.service;

import java.util.Calendar;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.bananaa.pass.dao.LoginDao;
import in.bananaa.pass.dao.MembershipDao;
import in.bananaa.pass.dto.GenericRequest.RequestType;
import in.bananaa.pass.dto.IdRequest;
import in.bananaa.pass.dto.member.MemberDto;
import in.bananaa.pass.dto.member.MembershipRequest;
import in.bananaa.pass.dto.member.MembershipResponse;
import in.bananaa.pass.dto.type.GenericErrorCodeType;
import in.bananaa.pass.entity.Member;
import in.bananaa.pass.entity.Membership;
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

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public MembershipResponse getMembership(IdRequest request) throws BusinessException {
		Optional<Membership> optionalMembership = dao.getMembership(request.getId());
		if (optionalMembership.isPresent()) {
			return mapper.map(optionalMembership.get());
		} else {
			throw new BusinessException(GenericErrorCodeType.MEMBERSHIP_NOT_FOUND);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void createOrUpdateMembership(MembershipRequest request) throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		if (request.getType() == RequestType.NEW) {
			Optional<User> optionalUser = loginDao.getUser(tokenHelper.getId());
			if (optionalUser.isPresent()) {
				User user = optionalUser.get();
				request.getMember().setType(RequestType.NEW);
				Member member = createOrUpdateMember(request.getMember());
				Membership membership = null;
				Optional<Membership> optionalMembership = dao.getMembership(user.getId(), member.getId());
				if (optionalMembership.isPresent()) {
					// this means that user initiated a new membership, when already a membership
					// exists -
					// update the same then
					membership = optionalMembership.get();
				} else {
					membership = new Membership(calendar, calendar);
					// set scan code only when membership is being created the very first time
					membership.setScanCode(scanCodeHelper.generateCode(member.getId(), user.getId()));
				}

				mapAndSaveMembership(request, membership, member, user);
			} else {
				LOG.error("Error occurred while trying to create membership without user. User Id : "
						+ tokenHelper.getId());
				throw new BusinessException(GenericErrorCodeType.USER_NOT_FOUND);
			}
		} else if (request.getType() == RequestType.UPDATE) {
			Optional<User> optionalUser = loginDao.getUser(tokenHelper.getId());
			if (optionalUser.isPresent()) {
				Optional<Membership> optionalMembership = dao.getMembership(request.getId());
				if (optionalMembership.isPresent()) {
					request.getMember().setType(RequestType.UPDATE);
					Member member = createOrUpdateMember(request.getMember());
					mapAndSaveMembership(request, optionalMembership.get(), member, optionalUser.get());
				} else {
					LOG.error("Error occurred while trying to update membership without a membership. User Id : "
							+ tokenHelper.getId() + ", Membership Id : " + request.getId());
					throw new BusinessException(GenericErrorCodeType.MEMBERSHIP_NOT_FOUND);
				}
			} else {
				LOG.error("Error occurred while trying to update membership without user. User Id : "
						+ tokenHelper.getId() + ", Membership Id : " + request.getId());
				throw new BusinessException(GenericErrorCodeType.USER_NOT_FOUND);
			}
		} else {
			throw new BusinessException(GenericErrorCodeType.GENERIC_ERROR);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public void blockMembership() {

	}

	private Member createOrUpdateMember(MemberDto request) throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		Member member = null;
		if (request.getType() == RequestType.NEW) {
			// check using phone if the user already exists in db, so that creating a new
			// membership doesn't create a new user every time
			Optional<Member> optionalMember = dao.getMemberByPhone(StringUtils.strip(request.getPhone()));
			if (optionalMember.isPresent()) {
				member = optionalMember.get();
			} else {
				member = new Member(calendar, calendar);
			}
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
		return member;
	}

	private void mapAndSaveMember(MemberDto request, Member member) {
		mapper.map(request, member);
		try {
			dao.saveMember(member);
		} catch(Exception e) {
			System.out.println("############################");
			System.out.println(e);
			System.out.println("############################");
		}
		
	}

	private void mapAndSaveMembership(MembershipRequest request, Membership membership, Member member, User user)
			throws BusinessException {
		mapper.map(request, membership, member, user);
		dao.saveMembership(membership);
	}
}
