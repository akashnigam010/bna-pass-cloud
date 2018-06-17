package in.bananaa.pass.dao;

import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.bananaa.pass.dto.user.LoginRequest;
import in.bananaa.pass.entity.User;

@Repository
public class LoginDao {
	@Autowired
	SessionFactory sessionFactory;

	public LoginDao() {
	}

	public LoginDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Optional<User> login(LoginRequest request) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
		criteria.add(Restrictions.eq("id", request.getId()));
		criteria.add(Restrictions.eq("password", request.getPassword()));
		User user = (User) criteria.uniqueResult();
		return Optional.ofNullable(user);
	}
}
