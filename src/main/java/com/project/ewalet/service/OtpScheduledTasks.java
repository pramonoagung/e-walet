package com.project.ewalet.service;

import com.project.ewalet.mapper.OtpMapper;
import com.project.ewalet.model.Otp;
import com.project.ewalet.utils.Validation;
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
    @Autowired
    private Validation validation;

    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs

    @Scheduled(cron = "0 0/3 * * * *")
    public void performTaskUsingCron() throws ParseException {
        List<Otp> otpList = otpMapper.getAllActiveOtp();

        for (Otp otp : otpList) {
            if (!validation.otpExpiry(otp.getCreated_at())) {
                otp.setStatus(false);
                otpMapper.update(otp);
            }
        }
    }
}
