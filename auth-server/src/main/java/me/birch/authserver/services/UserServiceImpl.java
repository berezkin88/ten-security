package me.birch.authserver.services;

import lombok.RequiredArgsConstructor;
import me.birch.authserver.entities.Otp;
import me.birch.authserver.entities.User;
import me.birch.authserver.repositories.OtpRepository;
import me.birch.authserver.repositories.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void auth(User user) {
        var userIfExists = userRepository.findUserByUsername(user.getUsername());

        userIfExists.ifPresentOrElse(user1 -> {
            if (passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
                renewOtp(user1);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        }, () -> {
            throw new BadCredentialsException("Bad credentials");
        });
    }

    private void renewOtp(User user) {
        var newCode = GenerateCodeUtil.generateCode();

        var otpIfExists = otpRepository.findOtpByUsername(user.getUsername());

        otpIfExists.ifPresentOrElse(otp -> otp.setCode(newCode),
            () -> {
                var newOtp = new Otp();
                newOtp.setUsername(user.getUsername());
                newOtp.setCode(newCode);
                otpRepository.save(newOtp);
            });
    }

    @Override
    public boolean validateOtp(Otp otp) {
        return otpRepository.findOtpByUsername(otp.getUsername())
            .map(otp1 -> otp.getCode().equals(otp1.getCode()))
            .orElse(false);
    }
}
