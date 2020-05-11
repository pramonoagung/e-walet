package com.project.ewalet.controller;

import com.project.ewalet.config.auth.JwtTokenUtil;
import com.project.ewalet.model.JwtRequest;
import com.project.ewalet.model.JwtResponse;
import com.project.ewalet.model.User;
import com.project.ewalet.model.UserDTO;
import com.project.ewalet.service.JwtUserDetailsService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        userDetailsService.updateToken(token, authenticationRequest.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO user) throws ValidationException {
        JSONObject jsonObject = new JSONObject();
        System.out.println(user);
        //TODO user input validation and respose
        String CustomValidationResponse = "failed";

        User savedUser = userDetailsService.save(user);
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

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}