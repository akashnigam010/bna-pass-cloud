package in.bananaa.pass.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao {
	@Autowired
	SessionFactory sessionFactory;

	public LoginDao() {
	}

	public LoginDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
