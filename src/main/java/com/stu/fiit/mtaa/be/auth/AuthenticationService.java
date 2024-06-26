package com.stu.fiit.mtaa.be.auth;

import com.stu.fiit.mtaa.be.appuser.AppUser;
import com.stu.fiit.mtaa.be.appuser.AppUserRepository;
import com.stu.fiit.mtaa.be.config.JwtService;
import com.stu.fiit.mtaa.be.exceptions.UsernameAlreadyTakenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws UsernameAlreadyTakenException {

        // Check if User with this username already exists
        Optional<AppUser> appUserOptional = repository.findByUsername(request.getUsername());
        if(appUserOptional.isPresent()){
            throw new UsernameAlreadyTakenException("User with this username already exists");
        }

        var user = AppUser.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .age(request.getAge())
                .fcmToken(request.getFcmToken())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AccessDeniedException {
       try {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           request.getUsername(),
                           request.getPassword()
                   )
           );
       } catch (AuthenticationException e) {
           throw new AccessDeniedException("Invalid username or password");
       }
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}

