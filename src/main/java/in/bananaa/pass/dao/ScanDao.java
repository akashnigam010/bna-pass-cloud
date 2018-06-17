package in.bananaa.pass.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ScanDao {
	@Autowired
	SessionFactory sessionFactory;

	public ScanDao() {
	}

	public ScanDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
