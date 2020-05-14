package com.project.ewalet.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class Validation {

    private final String EMAIL_REGEX = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";
    private final String NAME_REGEX = "(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$";
    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

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

    public boolean otpExpiry(String otpDateDb) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date otpDate = (Date) formatter.parse(otpDateDb);
        Calendar cal = Calendar.getInstance();
        cal.setTime(otpDate);
        long otpDateMilis = cal.getTimeInMillis();
        Date otpExpiry = new Date(otpDateMilis + (3 * ONE_MINUTE_IN_MILLIS));

        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date actualDate = new Date(t);

        if (actualDate.after(otpExpiry)) {
            return false;
        } else {
            return true;
        }
    }
}
