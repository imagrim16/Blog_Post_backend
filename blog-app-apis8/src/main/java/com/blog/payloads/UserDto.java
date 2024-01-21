package com.blog.payloads;



import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

private int id;
	
	@NotEmpty
	@Size(min=4,message="Username must be min of 4 characters")
	private String name;
	
	@Email(message="Email adress is invalid")
	private String email;
	
	@NotEmpty
	@Size(min=6,max=12,message="min characters 6 and maximum is 12")
	private String password;
	
	@NotEmpty
	private String about;
	
	 private Set<RoleDto> roles =new HashSet<>();
}
