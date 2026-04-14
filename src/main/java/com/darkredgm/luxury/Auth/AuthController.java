package com.darkredgm.luxury.Auth;

import com.darkredgm.luxury.User.Models.User;
import com.darkredgm.luxury.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<AuthResponse> login(@RequestBody SignInDto signInDto)
    {
        User user = userRepository.findByEmailAndPassword(signInDto.email(), signInDto.password());

        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.ok(
                new AuthResponse(
                        user,
                        "mytokenpromax"
                )
        );
    }
}
