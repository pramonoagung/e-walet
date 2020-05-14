package com.project.ewalet.service;

import com.project.ewalet.mapper.OtpMapper;
import com.project.ewalet.model.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class OtpScheduledTasks {

    @Autowired
    private OtpMapper otpMapper;

    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

    @Scheduled(cron = "0 0/5 * * * *")
    public void performTaskUsingCron() throws ParseException {
        List<Otp> otpList = otpMapper.getAllActiveOtp();

        for (Otp otp : otpList) {
            DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
            Date otpDate = (Date) formatter.parse(otp.getCreated_at());
            Calendar cal = Calendar.getInstance();
            cal.setTime(otpDate);
            long otpDateMilis = cal.getTimeInMillis();
            Date otpExpiry = new Date(otpDateMilis + (3 * ONE_MINUTE_IN_MILLIS));

            Calendar date = Calendar.getInstance();
            long t = date.getTimeInMillis();
            Date actualDate = new Date(t);
            
            if (actualDate.after(otpExpiry)) {
                otp.setStatus(false);
                otpMapper.update(otp);
            }
        }
    }
}
