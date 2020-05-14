package com.project.ewalet.controller;

import com.project.ewalet.config.auth.JwtTokenUtil;
import com.project.ewalet.mapper.OtpMapper;
import com.project.ewalet.mapper.UserBalanceMapper;
import com.project.ewalet.mapper.UserMapper;
import com.project.ewalet.model.JwtRequest;
import com.project.ewalet.model.Otp;
import com.project.ewalet.model.User;
import com.project.ewalet.model.UserBalance;
import com.project.ewalet.model.payload.OtpRequest;
import com.project.ewalet.model.payload.UserPayload;
import com.project.ewalet.service.JwtUserDetailsService;
import com.project.ewalet.service.rabbitmq.MQPublisher;
import com.project.ewalet.utils.Utility;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Configuration
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private OtpMapper otpMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserBalanceMapper userBalanceMapper;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        JSONObject jsonObject = new JSONObject();
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        User user = userMapper.findByPhoneNumber(authenticationRequest.getUsername());
        if (user.getStatus() == 0) {
            jsonObject.put("status", 401);
            jsonObject.put("message", "Please activate you account");
            return new ResponseEntity<>(jsonObject, HttpStatus.UNAUTHORIZED);
        } else {
            String token = jwtTokenUtil.generateToken(userDetails);
            JSONObject data = new JSONObject();
            data.put("token", token);
            data.put("message", "loged in");
            data.put("user_id", user.getId());
            jsonObject.put("status", 200);
            jsonObject.put("data", data);
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        // userDetailsService.updateToken(token, authenticationRequest.getEmail());
    }

    @Autowired
    private Utility utility;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserPayload user) throws Exception {
        JSONObject jsonObject = new JSONObject();

        User existingUserByPhoneNumber = userMapper.findByPhoneNumber(user.getPhone_number());
        User existingUserByEmail = userMapper.findByEmail(user.getEmail());
        if (existingUserByPhoneNumber != null) {
            jsonObject.put("status", 406);
            jsonObject.put("message", "User with phone number " + existingUserByPhoneNumber.getPhone_number() + " has already registered");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        }
        if (existingUserByEmail != null) {
            jsonObject.put("status", 406);
            jsonObject.put("message", "User with email " + existingUserByEmail.getEmail() + " has already registered");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        }

        //TODO user input validation and respose
        String CustomValidationResponse = "failed";

        String otpCode = utility.otpCode();

        User savedUser = userDetailsService.save(user);
        Otp otp = new Otp();
        otp.setUser_id(savedUser.getId());
        otp.setCode(otpCode);
        otp.setStatus(true);
        otp.setCreated_at(utility.getCurrentTimeOtp());
        otpMapper.save(otp);

        sendSms(savedUser.getPhone_number(), otpCode);
        sendEmail(savedUser.getEmail(), otpCode);

        if (savedUser != null) {
            jsonObject.put("status", 200);
            jsonObject.put("message", "created");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } else {
            jsonObject.put("status", 400);
            jsonObject.put("message", CustomValidationResponse);
            return new ResponseEntity<>(jsonObject, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestBody OtpRequest otpRequest) {
        JSONObject jsonObject = new JSONObject();
        //TODO logout
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);

    }

    @GetMapping(value = "/get-user-profile")
    public ResponseEntity<?> getUserProfile(Authentication authentication) {
        User userProfile = userMapper.findByPhoneNumber(authentication.getName());
        JSONObject jsonResponse = new JSONObject();
        if (userProfile != null) {
            jsonResponse.put("status", 200);
            JSONObject data = new JSONObject();
            data.put("first_name", userProfile.getFirst_name());
            data.put("last_name", userProfile.getLast_name());
            data.put("email", userProfile.getEmail());
            data.put("phone_number", userProfile.getPhone_number());
            jsonResponse.put("data", data);
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            jsonResponse.put("status", 204);
            jsonResponse.put("message", "Balance for user " + authentication.getName() + "is empty");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping(value = "/get-user-balance")
    public ResponseEntity<?> getUserBalance(Authentication authentication) {
        UserBalance userBalance = userBalanceMapper.findByUserId(userMapper.findByPhoneNumber(authentication.getName()).getId());
        JSONObject jsonResponse = new JSONObject();
        if (userBalance != null) {
            jsonResponse.put("status", 200);
            jsonResponse.put("data", new JSONObject().put("amount", userBalance.getBalance()));
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }
        else {
            jsonResponse.put("status", 404);
            jsonResponse.put("message", "Balance for user " + authentication.getName() + " is empty");
            return new ResponseEntity<>(jsonResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
        JSONObject jsonObject = new JSONObject();
        Otp otp = otpMapper.findByCode(otpRequest.getOtp_code());
        if (otp == null) {
            jsonObject.put("status", 404);
            jsonObject.put("message", "OTP Code not found");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_FOUND);
        } else if (!otp.isStatus()) {
            jsonObject.put("status", 401);
            jsonObject.put("message", "OTP Code is expired");
            return new ResponseEntity<>(jsonObject, HttpStatus.NOT_ACCEPTABLE);
        } else {
            User user = userMapper.getById(otp.getUser_id());
            user.setStatus(1);
            userMapper.update(user);
            otp.setStatus(false);
            otpMapper.update(otp);
            jsonObject.put("status", 200);
            jsonObject.put("message", "Verified");
            return new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @Autowired
    private MQPublisher mqPublisher;

    void sendSms(String phoneNumber, String otpCode) {
        JSONObject jsonSendSms = new JSONObject();
        jsonSendSms.put("phoneNumber", phoneNumber);
        jsonSendSms.put("otpCode", otpCode);
        mqPublisher.mqSendSms(jsonSendSms.toString());
    }

    void sendEmail(String toEmail, String otpCode) {
        JSONObject jsonSendEmail = new JSONObject();
        jsonSendEmail.put("toEmail", toEmail);
        jsonSendEmail.put("otpCode", otpCode);
        mqPublisher.mqSendEmail(jsonSendEmail.toString());
    }

}