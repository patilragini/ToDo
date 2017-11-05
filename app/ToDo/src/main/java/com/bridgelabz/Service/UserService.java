package com.bridgelabz.Service;

import com.bridgelabz.Model.UserDetails;

public interface UserService {
	public int createUser(UserDetails user);
	public UserDetails loginUser(UserDetails user);
	public UserDetails emailValidation(String email);
	public UserDetails getUserById(int id);
}

