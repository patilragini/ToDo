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
			System.out.println("in transaction");
			id = (Integer) session.save(userDetails);
			System.out.println(id);
			transaction.commit();
			return id;
		} catch (Exception e) {
			System.out.println("registration error!!!");
			e.printStackTrace();
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
		System.out.println("in login" + email);
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

	// logic for existing email
	public List emailValidation(String email) {
		Session session = sessionFactory.openSession();
		TypedQuery<UserDetails> query = session.createQuery("select U.activated from UserDetails U where email=:email");
		query.setParameter("email", email);
//		query.setParameter("password", password);
		List<UserDetails> list = query.getResultList();	
		System.out.println("list:"+list+"list value:"+list.get(0));
		if (list.isEmpty()) {
			return null;
		} else {
			session.close();
			String a = list.toString();
			System.out.println("here" + a);
			return list;
		}
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
			System.out.println(UserDetails.class);
			System.out.println("1");
			UserDetails active = session.get(UserDetails.class, id);
			active.setActivated(id);
			session.update(active);
			System.out.println("2");
			transaction.commit();
			return true;
		}
		return false;

	}
}
