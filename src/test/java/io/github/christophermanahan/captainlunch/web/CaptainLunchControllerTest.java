package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.service.CreateUserService;
import io.github.christophermanahan.captainlunch.service.RotationService;
import io.github.christophermanahan.captainlunch.service.ValidationService;
import io.github.christophermanahan.captainlunch.web.slack.UserProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CaptainLunchControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateUserService userService;

    @MockBean
    private ValidationService validationService;

    @MockBean
    private RotationService rotationService;

    @MockBean
    private Client client;

    @Test
    @SuppressWarnings("unchecked")
    void createsUserIfRequestIsValid() throws Exception {
        String identity = "W100000";
        String displayName = "John Doe";
        UserProfile profile = new UserProfile();
        profile.setReal_name(displayName);
        when(validationService.validateRequest(any(HttpEntity.class))).thenReturn(true);
        when(userService.createUser(identity)).thenReturn(new User(identity, new Date(Long.valueOf("0"))));
        when(client.getUserProfile(identity)).thenReturn(profile);

        mvc.perform(post("/join")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("user_id", identity))
            .andExpect(status().isCreated());
    }

    @Test
    @SuppressWarnings("unchecked")
    void joinIsUnauthorizedIfRequestIsInvalid() throws Exception {
        String identity = "W100000";
        when(validationService.validateRequest(any(HttpEntity.class))).thenReturn(false);

        mvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user_id", identity))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SuppressWarnings("unchecked")
    void isFoundIfUserAlreadyExist() throws Exception {
        String identity = "W100000";
        when(userService.createUser(identity)).thenThrow(DataIntegrityViolationException.class);
        when(validationService.validateRequest(any(HttpEntity.class))).thenReturn(true);

        mvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user_id", identity))
                .andExpect(status().isOk());
    }

    @Test
    @SuppressWarnings("unchecked")
    void notifiesUsersWhenOverridden() throws Exception {
        String identity = "W100000";
        String displayName = "John Doe";
        UserProfile profile = new UserProfile();
        profile.setReal_name(displayName);
        when(validationService.validateRequest(any(HttpEntity.class))).thenReturn(true);
        when(client.getUserProfile(identity)).thenReturn(profile);

        mvc.perform(post("/override")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user_id", identity))
                .andExpect(status().isOk());

        verify(rotationService).rotateIntoHead(identity);
        verify(client).notifyUsers(displayName + " overrode the rotation and is your new lunch captain!");
    }

    @Test
    @SuppressWarnings("unchecked")
    void overrideIsUnauthorizedIfRequestIsInvalid() throws Exception {
        String identity = "W100000";
        when(validationService.validateRequest(any(HttpEntity.class))).thenReturn(false);

        mvc.perform(post("/override")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user_id", identity)
                .param("display_name", "Test"))
                .andExpect(status().isUnauthorized());
    }
}
