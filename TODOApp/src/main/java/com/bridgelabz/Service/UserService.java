package com.bridgelabz.Service;

import com.bridgelabz.Model.UserDetails;

public interface UserService {
	public void createUser(UserDetails user);
	public UserDetails loginUser(UserDetails user);
	public UserDetails emailValidation(String email);
}
