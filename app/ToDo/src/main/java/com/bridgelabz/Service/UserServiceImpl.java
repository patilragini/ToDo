package com.bridgelabz.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Dao.UserDao;
import com.bridgelabz.Model.UserDetails;

/**
 * 
 * @author Ragini
 *         <p>
 * 		class UserServiceImpl implements UserService
 *         <p>
 * 		public void createUser(UserDetails user) {
 *         userDao.registration(user);
 * 
 *         }
 * 
 *         <p>
 * 		public UserDetails loginUser(UserDetails user) { return
 *         userDao.login(user); }
 * 
 *         <p>
 * 		public UserDetails emailValidation(String email) { return null; }
 */
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	public int createUser(UserDetails user) {
		// TODO Auto-generated method stub
		int id = userDao.registration(user);
		if (id != 0)
			return id;
		else
			return 0;

	}

	public UserDetails loginUser(UserDetails user) {
		// TODO Auto-generated method stub
		return userDao.login(user);
	}

	public UserDetails emailValidation(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	public UserDetails getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
	} 
	
	

}
