package me.birch.businessserver.services;

public interface AuthenticationServerProxy {
    void sendAuth(String username, String password);
    boolean sendOtp(String username, String otp);
}
