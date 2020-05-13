package com.project.ewalet.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validation {

    private final String EMAIL_REGEX = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
    private final String NAME_REGEX = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$";

    public boolean name(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }

    public boolean email(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public boolean phoneNumber(String phoneNumber) {
        if (phoneNumber.charAt(0) != '0') {
            if (phoneNumber.length() >= 10 && phoneNumber.length() <= 13) {
                return true;
            } else {
                return false;
            }
        } else if (phoneNumber.charAt(0) == '6' && phoneNumber.charAt(1) == '2') {
            if (phoneNumber.length() >= 10 && phoneNumber.length() <= 13) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
