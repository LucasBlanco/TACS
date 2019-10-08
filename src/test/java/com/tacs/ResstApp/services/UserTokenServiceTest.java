package com.tacs.ResstApp.services;

import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.exceptions.InvalidTokenException;
import com.tacs.ResstApp.services.impl.UserTokenService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTokenServiceTest {

    UserTokenService userTokenService = new UserTokenService();

    @Test
    public void aTokenIsGenerated() {
        User user = new User();
        user.setId(1L);
        int numberOfTokens = userTokenService.getTokenMap().size();

        userTokenService.generateToken(user);

        assertThat(userTokenService.getTokenMap().size()).isEqualTo(numberOfTokens + 1);
    }

    @Test
    public void aTokenIsRemoved() {
        User user = new User();
        user.setId(1L);
        String token = userTokenService.generateToken(user);
        int numberOfTokens = userTokenService.getTokenMap().size();

        userTokenService.destroyToken(token);

        assertThat(userTokenService.getTokenMap().size()).isEqualTo(numberOfTokens - 1);
    }
    
    @Test
    public void getUserFromToken() throws InvalidTokenException {
    	User user = new User();
        user.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        
        String token = userTokenService.generateToken(user);
        String token2 = userTokenService.generateToken(user2);
        
        assertEquals(user, userTokenService.getUser(token));
        assertEquals(user2, userTokenService.getUser(token2));
        userTokenService.destroyToken(token);
        assertThrows(InvalidTokenException.class, () -> {userTokenService.getUser(token);});
    }
}
