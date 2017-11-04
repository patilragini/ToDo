package com.bridgelabz.Dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Model.UserDetails;

public class UserDaoImpl implements UserDao{
	
	@Autowired
	SessionFactory sessionFactory;
	/**
	 *  registration is implementation logic.
	 */
	public void registration(UserDetails userDetails) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction transaction=null;
		try {
		transaction = session.beginTransaction();
		session.save(userDetails);
		transaction.commit();
		}catch(Exception e){
			System.out.println("registration error!!!");
			e.printStackTrace();
			}
		finally {
			if (session != null)
			session.close();
			
		}
		
	}
/**
 *Login Logic Implimentation body of login
 */
	public UserDetails login(UserDetails userDetails) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		String email=userDetails.getEmail();
		String password=userDetails.getPassword();
		System.out.println("in login"+email);	
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", userDetails.getEmail()));
		criteria.add(Restrictions.eq("password", userDetails.getPassword()));
		UserDetails user=(UserDetails) criteria.uniqueResult();
		System.out.println(user.getEmail());
		System.out.println(user.getName());
		System.out.println(user.getPhoneNumber());
		System.out.println(user.getPassword());
		if(user!=null)
		{
			return user;
			
		}
		else return null;
	}

	public UserDetails emailValidation(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
