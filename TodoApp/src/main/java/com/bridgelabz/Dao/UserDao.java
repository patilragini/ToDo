package com.bridgelabz.Dao;

import java.util.List;
import java.util.Set;

import com.bridgelabz.Model.UserDetails;
import com.bridgelabz.Model.Label;

/**
 * 
 * @author Ragini interface UserDao
 *         <p>
 * 		interface methpds:
 *         <p>
 * 		public void registration(UserDetails userDetails);
 *         <p>
 * 		public UserDetails login(UserDetails userDetails);
 *         <p>
 * 		public UserDetails emailValidation(String email);
 */
public interface UserDao {
	/**
	 * 
	 * @param userDetails
	 *            interface method
	 * @return
	 */
	public int registration(UserDetails userDetails);

	/**
	 * 
	 * @param userDetails
	 *            interface method
	 */
	public UserDetails login(UserDetails userDetails);

	/**
	 * 
	 * @param email
	 * @return UserDetails interface method
	 */
	public UserDetails getUserByEmail(String email);

	UserDetails getUserById(int id);
	
	public boolean updateActivation(int id);
	public boolean updateUser(UserDetails user);
	public int addLabel(Label label);
	public boolean deleteLable(Label label);
	public boolean updateLable(Label label);
	public Set<Label> getAllLabels(int userId);
}
