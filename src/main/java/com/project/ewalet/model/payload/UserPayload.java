package com.project.ewalet.model.payload;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UserPayload {
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Invalid email format")
	private String email;
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$", message = "The password must contain at least 1 lowercase, 1 uppercase, 1 numeric, and more than 8 character")
	private String password;
	private String first_name;
	private String last_name;
	@Pattern(regexp = "(\\+62 ((\\d{3}([ -]\\d{3,})([- ]\\d{4,})?)|(\\d+)))|(\\(\\d+\\) \\d+)|\\d{3}( \\d+)+|(\\d+[ -]\\d+)|\\d+", message = "Must inclide country code (62) and length 11 to 12 ")
	private String phone_number;
}