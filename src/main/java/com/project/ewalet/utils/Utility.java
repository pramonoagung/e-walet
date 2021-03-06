package com.project.ewalet.utils;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

@Component
public class Utility {
    public String otpCode() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }

    public String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public String getCurrentTimeOtp() {
        DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
