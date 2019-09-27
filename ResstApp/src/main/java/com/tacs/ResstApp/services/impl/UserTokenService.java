package com.tacs.ResstApp.services.impl;

import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.InvalidTokenException;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;

@Component
public class UserTokenService {

    //Usuario y string su token
    private HashMap<String, User> tokenMap = new HashMap<>();

    public String generateToken(User user){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);
        String token = bytes.toString();
        tokenMap.put(token, user);
        return token;
    }
    
    public boolean validateLogged(String token) throws InvalidTokenException {
    	if (!tokenMap.containsKey(token)) {
    		return false;
    	} else return true;
    }
    
    public boolean validateAdmin(String token) throws InvalidTokenException {
    	this.validateLogged(token);
    	User user = tokenMap.get(token);
    	if(!user.isAdmin()) {
    		return false;
    	} else return true;
    }
    
    //public void validateUser()

    public void destroyToken(String token) {
        tokenMap.remove(token);
    }

    public HashMap<String, User> getTokenMap() {
        return tokenMap;
    }
    
    public User getUser(String token) throws InvalidTokenException {
    	if (!tokenMap.containsKey(token)) {
    		throw new InvalidTokenException(token);
    	}
    		
    	return tokenMap.get(token);
    }
}
