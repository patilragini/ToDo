package com.bridgelabz.Service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.Dao.UserDao;
import com.bridgelabz.Model.Label;
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

	public UserDetails getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userDao.getUserByEmail(email);
	}

	public UserDetails getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
	}

	@Override
	public boolean updateActivation(int id) {
		// TODO Auto-generated method stub
		return userDao.updateActivation(id);
	}

	@Override
	public boolean updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		return userDao.updateUser(user);
	} 
	
	public int addLabel(Label label) {
		return userDao.addLabel(label);
		
	}

	public boolean deleteLable(Label label) {
		// TODO Auto-generated method stub
		return userDao.deleteLable(label);
	}

	public boolean updateLable(Label label) {
		// TODO Auto-generated method stub
		return userDao.updateLable(label);
	}

	public Set<Label> getAllLabels(int userId) {
		// TODO Auto-generated method stub
		return userDao.getAllLabels(userId);
	}

	@Override
	public List<UserDetails> getUserList() {
		// TODO Auto-generated method stub
		return null;
	}

}
