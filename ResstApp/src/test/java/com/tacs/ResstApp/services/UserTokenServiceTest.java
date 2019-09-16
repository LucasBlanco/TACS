package com.tacs.ResstApp.services;

import com.tacs.ResstApp.model.User;
import com.tacs.ResstApp.services.impl.UserTokenService;
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
}
