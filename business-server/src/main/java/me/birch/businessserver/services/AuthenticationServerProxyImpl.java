package me.birch.businessserver.services;

import lombok.RequiredArgsConstructor;
import me.birch.businessserver.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AuthenticationServerProxyImpl implements AuthenticationServerProxy {

    private final RestTemplate restTemplate;

    @Value("${auth.server.base.url}")
    private String baseUrl;

    @Override
    public void sendAuth(String username, String password) {
        var url = baseUrl + "/users/auth";

        var body = new User();
        body.setUsername(username);
        body.setPassword(password);

        var request = new HttpEntity<>(body);

        restTemplate.postForEntity(url, request, Void.class);
    }

    @Override
    public boolean sendOtp(String username, String otp) {
        var url = baseUrl + "/otp/check";

        var body = new User();
        body.setUsername(username);
        body.setCode(otp);

        var request = new HttpEntity<>(body);

        var response = restTemplate.postForEntity(url, request, Void.class);

        return response
            .getStatusCode()
            .isSameCodeAs(HttpStatusCode.valueOf(200));
    }
}
