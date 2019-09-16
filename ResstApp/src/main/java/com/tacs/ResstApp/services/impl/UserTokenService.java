package com.tacs.ResstApp.services.impl;

import com.tacs.ResstApp.model.User;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;

@Component
public class UserTokenService {

    //Long es el id de usuario y string su token
    private HashMap<String, Long> tokenMap = new HashMap<>();

    public String generateToken(User user){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();

        tokenMap.put(token, user.getId());

        return token;
    }

    public void destroyToken(String token) {
        tokenMap.remove(token);
    }

    public HashMap<String, Long> getTokenMap() {
        return tokenMap;
    }
}
