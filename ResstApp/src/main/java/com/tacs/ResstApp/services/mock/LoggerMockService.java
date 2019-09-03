package com.tacs.ResstApp.services.mock;

import com.tacs.ResstApp.services.exceptions.ServiceException;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class LoggerMockService {

    private List<String> tokens;

    public LoggerMockService() {
        this.tokens = new ArrayList<>(Arrays.asList("token1", "token2", "token3"));
    }

    public String login(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        this.tokens.add(token);
        return token;
    }

    public void logout(String token) throws ServiceException {
        this.tokens.removeIf(t -> t.equals(token));
    }

}

