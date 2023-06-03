package me.birch.authserver.controllers;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.birch.authserver.entities.Otp;
import me.birch.authserver.entities.User;
import me.birch.authserver.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/users/add")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PostMapping("/users/auth")
    public void authUser(@RequestBody User user) {
        userService.auth(user);
    }

    @PostMapping("/otp/check")
    public void checkOtp(@RequestBody Otp otp, HttpServletResponse response) {
        if (userService.validateOtp(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
