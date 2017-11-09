package com.bridgelabz.Dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.utility.MD5Encryption;

public class UserDaoImpl implements UserDao {

	@Autowired
	SessionFactory sessionFactory;

	/**
	 * registration is implementation logic.
	 */
	public int registration(UserDetails userDetails) {
		System.out.println("in registration!!!");
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int id;
		try {
			transaction = session.beginTransaction();
			// System.out.println("in transaction");
			id = (Integer) session.save(userDetails);
			System.out.println(id);
			transaction.commit();
			return id;
		} catch (Exception e) {
			System.out.println("Email Exist!!!");
			// e.printStackTrace();
			return 0;
		} finally {
			if (session != null)
				session.close();
		}

	}

	/**
	 * Login Logic Implimentation body of login
	 */
	public UserDetails login(UserDetails userDetails) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String email = userDetails.getEmail();
		String password = userDetails.getPassword();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", userDetails.getEmail()));
		criteria.add(Restrictions.eq("password", userDetails.getPassword()));
		UserDetails user = (UserDetails) criteria.uniqueResult();
		if (user != null) {
			System.out.println("FOUND:: " + user.getActivated() + user.getEmail() + user.getId() + user.getPassword());
			return user;

		} else
			return null;
	}

	// logic for existing email && get user by email
	/**
	 * Accepts email and returns user
	 */
	public UserDetails emailValidation(String email) {// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", email));		
		UserDetails user = (UserDetails) criteria.uniqueResult();
		if (user != null) {
			//System.out.println("FOUND:: "+user.getActivated()+user.getEmail()+user.getId()+user.getPassword());
				return user;

		} else
			return null;
	}

	/*
	 * getUserById is implementation logic.
	 * 
	 * @return userDetails
	 */
	public UserDetails getUserById(int id) {
		Session session = sessionFactory.openSession();
		UserDetails userDetails = session.get(UserDetails.class, id);
		session.close();
		return userDetails;
	}

	@Override
	public boolean updateActivation(int id) {
		// TODO Auto-generated method stub
		System.out.println("id:" + id);
		if (id > 0) {
			System.out.println("id found");
			Session session = sessionFactory.openSession();
			Transaction transaction = null;
			transaction = session.beginTransaction();			
			UserDetails active = session.get(UserDetails.class, id);
			active.setActivated(id);
			session.update(active);
			transaction.commit();
			return true;
		}
		return false;

	}

	@Override
	public boolean updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		int id;
		try {
			System.out.println(user);

			transaction = session.beginTransaction();
			// System.out.println("in transaction");
			session.saveOrUpdate(user);
			System.out.println(user);
			transaction.commit();
			return true;
		} catch (Exception e) {
			System.out.println("CANNOT UPDATE USER");
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}
}
