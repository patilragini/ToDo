package com.bridgelabz.Service;

import java.util.List;

import com.bridgelabz.Model.UserDetails;

public interface UserService {
	public int createUser(UserDetails user);
	public UserDetails loginUser(UserDetails user);
	public UserDetails getUserByEmail(String email);
	public UserDetails getUserById(int id);
	public boolean updateActivation(int id);
	public boolean updateUser(UserDetails user);

}
