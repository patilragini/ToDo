package com.bridgelabz.Dao;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bridgelabz.Model.UserDetails;

//@EnableTransactionManagement
public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;
	/*
	 * @Autowired Transactional txManager;
	 */

	/**
	 * registration is implementation logic.
	 */
//	@Transactional()
	public void registration(UserDetails userDetails) {
		// TODO Auto-generated method stub

		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int id = 0;
		System.out.println(userDetails.getEmail());
		try {
			transaction = session.beginTransaction(); 
			System.out.println("dfd");
//			sessionFactory.getCurrentSession().save(userDetails);
			 id = (Integer) session.save(userDetails); 
			// session.persist(userDetails);
			System.out.println("Id:" + id);
			 session.close(); 
			 transaction.commit(); 
		} catch (Exception e) {
			System.out.println("registration error!!!");
			e.printStackTrace();
		}
		/*
		 * if (session != null) session.close();
		 * 
		 * }
		 */

	}

	/**
	 * Login Logic Implimentation body of login
	 */
	public UserDetails login(UserDetails userDetails) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String email = userDetails.getEmail();
		String password = userDetails.getPassword();
		System.out.println("in login :" + email);
		
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", userDetails.getEmail()));
		criteria.add(Restrictions.eq("password", userDetails.getPassword()));
		UserDetails user = (UserDetails) criteria.uniqueResult();
		if (user != null) {
			System.out.println(user.getEmail());
			System.out.println(user.getName());
			System.out.println(user.getPhoneNumber());
			System.out.println(user.getPassword());
			return user;

		} else
			return null;
	}

	public UserDetails emailValidation(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
