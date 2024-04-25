package com.edcarlitos.conctactapi.controller;

import com.edcarlitos.conctactapi.dto.UserLoginDTO;
import com.edcarlitos.conctactapi.dto.UserRegisterDTO;
import com.edcarlitos.conctactapi.dto.UserResponse;
import com.edcarlitos.conctactapi.entity.UserEntity;
import com.edcarlitos.conctactapi.exceptions.UserAlreadyExist;
import com.edcarlitos.conctactapi.service.IUserDetailsService;
import com.edcarlitos.conctactapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRegisterDTO userRegisterDTO) throws UserAlreadyExist {
        return ResponseEntity.status(HttpStatus.CREATED).body(userDetailsService.registerUser(userRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserLoginDTO userLoginDTO) throws UsernameNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(userDetailsService.loginUser(userLoginDTO));
     }

     @GetMapping("/")
    public String hello(){
        return "Hello World";
     }

}
