package com.blog.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.blog.payloads.UserDto;

public interface UserService {

	UserDto registerNewUser(UserDto user);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	UserDto getUserById(Integer userId);
	void deleteUser(Integer userId);
	List<UserDto> getAllUsers();

}


