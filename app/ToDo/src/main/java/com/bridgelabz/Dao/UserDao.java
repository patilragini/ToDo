package com.bridgelabz.Dao;

import com.bridgelabz.Model.UserDetails;
/**
 * 
 * @author Ragini
 * interface UserDao
 * <p>interface methpds:
 * <p>public void registration(UserDetails userDetails);
 *<p>public UserDetails login(UserDetails userDetails);
 *<p>public UserDetails emailValidation(String email);
 */
public interface UserDao {
	/**
	 * 
	 * @param userDetails
	 * interface method 
	 * @return 
	 */
	public int registration(UserDetails userDetails);
	/**
	 * 
	 * @param userDetails
	 * interface method 
	 */
	public UserDetails login(UserDetails userDetails);
	/**
	 * 
	 * @param email
	 * @return UserDetails
	 * interface method 
	 */
	public UserDetails emailValidation(String email);
	
	/**
	 * @param id
	 * @return user 
	 * interface method
	 */
	UserDetails getUserById(int id);
}
