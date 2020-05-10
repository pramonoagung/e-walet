package com.project.ewalet.model;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserDTO {
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Invalid email format")
	private String email;
	@Pattern(regexp = "^(?:(?=.?\\p{N})(?=.?[\\p{S}\\p{P} ])(?=.?\\p{Lu})(?=.?\\p{Ll}))[^\\p{C}]{8,16}$", message = "The password must contain at least 1 lowercase, 1 uppercase, 1 numeric, 1 special character, and more than 8 character")
	private String password;
	private String first_name;
	private String last_name;
	@Pattern(regexp = "(^\\d+$)", message = "Use number only for phone number")
	private String phone_number;
}