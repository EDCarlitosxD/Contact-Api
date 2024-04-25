package com.edcarlitos.conctactapi.service;

import com.edcarlitos.conctactapi.dto.UserLoginDTO;
import com.edcarlitos.conctactapi.dto.UserRegisterDTO;
import com.edcarlitos.conctactapi.dto.UserResponse;
import com.edcarlitos.conctactapi.entity.UserEntity;
import com.edcarlitos.conctactapi.exceptions.UserAlreadyExist;
import com.edcarlitos.conctactapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserResponse registerUser(UserRegisterDTO userRegisterDTO) throws UserAlreadyExist {
        if(userRepository.existsByEmail(userRegisterDTO.email())) throw new UserAlreadyExist();

        //Save in DB
        UserEntity userEntity = new UserEntity(
                null,
                userRegisterDTO.username(),
                passwordEncoder.encode(userRegisterDTO.password()),
                userRegisterDTO.email()
        );

        userEntity = userRepository.save(userEntity);


        //Generate Token
        String token = jwtService.generateToken(userEntity);

        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userRegisterDTO.email(),
                userRegisterDTO.password()
        ));
        SecurityContextHolder.getContext().setAuthentication(auth);


        return new UserResponse("Registro completo",token);
    }

    public UserResponse loginUser(UserLoginDTO userLoginDTO) throws UsernameNotFoundException {
        Optional<UserEntity> userFind =  userRepository.findByEmail(userLoginDTO.email());

        if(userFind.isEmpty())throw new UsernameNotFoundException("El usuario no existe");


        UsernamePasswordAuthenticationToken tokenAuth = new UsernamePasswordAuthenticationToken(
                userLoginDTO.email(),
                userLoginDTO.password());
        Authentication auth = authenticationManager.authenticate(tokenAuth);
        SecurityContextHolder.getContext().setAuthentication(auth);


        String tokenJwt = jwtService.generateToken(userFind.get());
        return new UserResponse("Login existoso",tokenJwt);
    }
}
