package in.bananaa.pass.dao;

import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import in.bananaa.pass.dto.scan.ScanRequest;
import in.bananaa.pass.entity.Membership;
import in.bananaa.pass.entity.Scan;

@Repository
public class ScanDao {
	@Autowired
	SessionFactory sessionFactory;

	public ScanDao() {
	}

	public ScanDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Optional<Membership> getMembership(ScanRequest request, Integer userId) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Membership.class);
		criteria.createAlias("user", "user");
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.add(Restrictions.eq("scanCode", request.getCode()));
		Membership membership = (Membership) criteria.uniqueResult();
		return Optional.ofNullable(membership);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void saveScan(Scan scan) {
		sessionFactory.getCurrentSession().saveOrUpdate(scan);
	}
}
