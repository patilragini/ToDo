package com.bridgelabz.Service;

import java.util.List;
import java.util.Set;

import com.bridgelabz.Model.Label;
import com.bridgelabz.Model.UserDetails;

public interface UserService {
	public int createUser(UserDetails user);
	public UserDetails loginUser(UserDetails user);
	public UserDetails getUserByEmail(String email);
	public UserDetails getUserById(int id);
	public boolean updateActivation(int id);
	public boolean updateUser(UserDetails user);
	public int addLabel(Label label);
	public boolean deleteLable(Label label);
	public boolean updateLable(Label label);
	public Set<Label> getAllLabels(int userId);
	public List<UserDetails> getUserList();

}
