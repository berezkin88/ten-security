package me.birch.authserver.services;

import me.birch.authserver.entities.Otp;
import me.birch.authserver.entities.User;

public interface UserService {

    void addUser(User user);
    void auth(User user);
    boolean validateOtp(Otp otp);
}
