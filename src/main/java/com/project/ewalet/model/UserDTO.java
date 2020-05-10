package com.project.ewalet.model;

import lombok.Data;

@Data
public class UserDTO {
	private String email;
	private String password;
	private String first_name;
	private String last_name;
	private String phone_number;
}