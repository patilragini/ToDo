package com.bridgelabz.Dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Model.UserDetails;

public class UserDaoImpl implements UserDao{
	
	@Autowired
	SessionFactory sessionFactory;
	/**
	 *  registration is implementation logic.
	 */
	public int registration(UserDetails userDetails) {
		System.out.println("in registration!!!");
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		Transaction transaction=null;
		int id;
		try {			
		transaction = session.beginTransaction();
		System.out.println("in transaction");
		id=(Integer)session.save(userDetails);
		System.out.println(id);
		transaction.commit();
		return id;
		}catch(Exception e){
			System.out.println("registration error!!!");
			e.printStackTrace();
			return 0;		
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
		if(user!=null)
		{
			System.out.println(user.getEmail());
			System.out.println(user.getName());
			System.out.println(user.getPhoneNumber());
			System.out.println(user.getPassword());
			return user;
			
		}
		else return null;
	}
//logic for existing email 
	public UserDetails emailValidation(String email) {
		// TODO Auto-generated method stub
		
		return null;
	}

}
