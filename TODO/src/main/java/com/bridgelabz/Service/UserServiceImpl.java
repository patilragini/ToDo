package com.bridgelabz.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Dao.UserDao;
import com.bridgelabz.Model.UserDetails;
/**
 * 
 * @author Ragini
 *<p>class UserServiceImpl implements UserService
 *<p>public void createUser(UserDetails user) {
		userDao.registration(user);
		
	}

	<p>public UserDetails loginUser(UserDetails user) {
		return userdao.login(user);
	}

	<p>public UserDetails emailValidation(String email) {
		return null;
	}
 */
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userdao;
	
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub
		userdao.registration(user);
		
	}

	public UserDetails loginUser(UserDetails user) {
		// TODO Auto-generated method stub
		return userdao.login(user);
	}

	public UserDetails emailValidation(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
