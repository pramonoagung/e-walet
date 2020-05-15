package com.project.ewalet.model.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class UserPayload {
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Invalid email format")
    @NotNull(message = "Email must not be null")
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$", message = "The password must contain at least 1 lowercase, 1 uppercase, 1 numeric, and more than 8 character")
    @NotNull(message = "Password must not be null")
    private String password;
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "Max first name length is 25")
    @NotNull(message = "First name must not be null")
    private String first_name;
    @Pattern(regexp = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$", message = "Max last name length is 25")
    @NotNull(message = "Last name must not be null")
    private String last_name;
    @Pattern(regexp = "(\\()?(\\+62|62|0)(\\d{2,3})?\\)?[ .-]?\\d{2,4}[ .-]?\\d{2,4}[ .-]?\\d{2,4}", message = "Must inclide country code (62) and length 10 to 13 ")
    @NotNull(message = "Phone number must not be null")
    private String phone_number;
}