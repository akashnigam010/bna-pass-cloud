package in.bananaa.pass.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.bananaa.pass.dto.PageRequest;
import in.bananaa.pass.dto.member.BlockMembershipRequest;
import in.bananaa.pass.entity.Member;
import in.bananaa.pass.entity.Membership;
import in.bananaa.pass.helper.exception.BusinessException;

@Repository
public class MembershipDao {
	@Autowired
	SessionFactory sessionFactory;

	public MembershipDao() {
	}

	public MembershipDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Optional<Member> getMember(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Member.class);
		criteria.add(Restrictions.eq("id", id));
		Member member = (Member) criteria.uniqueResult();
		return Optional.ofNullable(member);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Optional<Membership> getMembership(Integer id) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Membership.class);
		criteria.add(Restrictions.eq("id", id));
		Membership membership = (Membership) criteria.uniqueResult();
		return Optional.ofNullable(membership);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
	public Optional<List<Membership>> getMemberships(PageRequest pageRequest, Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Membership.class);
		criteria.createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id", userId));
		int firstResult = ((pageRequest.getPageNumber() - 1) * pageRequest.getResultsPerPage());
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(pageRequest.getResultsPerPage());
		List<Membership> memberships = criteria.list();
		return Optional.ofNullable(memberships);
	}

	public Optional<Membership> getMembership(Integer userId, Integer memberId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Membership.class);
		criteria.createAlias("user", "user");
		criteria.createAlias("member", "member");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("member.id", memberId));
		Membership membership = (Membership) criteria.uniqueResult();
		return Optional.ofNullable(membership);
	}

	public Optional<Membership> getMembership(BlockMembershipRequest request, Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Membership.class);
		criteria.createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("id", request.getId()));
		Membership membership = (Membership) criteria.uniqueResult();
		return Optional.ofNullable(membership);
	}

	public void saveMember(Member member) {
		sessionFactory.getCurrentSession().saveOrUpdate(member);
	}

	public void saveMembership(Membership membership) {
		sessionFactory.getCurrentSession().saveOrUpdate(membership);
	}
}
