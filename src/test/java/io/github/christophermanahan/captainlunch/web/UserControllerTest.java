package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.service.CreateUserService;
import io.github.christophermanahan.captainlunch.service.SlackSigningSecretValidationService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateUserService userService;

    @MockBean
    private SlackSigningSecretValidationService slackSigningSecretValidationService;

    @Test
    @SuppressWarnings("unchecked")
    void createsUserIfRequestIsValid() throws Exception {
        String identity = "W100000";
        when(userService.createUser(identity)).thenReturn(new User(identity, new Date(Long.valueOf("0"))));
        when(slackSigningSecretValidationService.validateRequest(any(HttpEntity.class))).thenReturn(true);

        mvc.perform(post("/join")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("user_id", identity)
            .param("user_name", "Test"))
            .andExpect(status().isCreated());
    }

    @Test
    @SuppressWarnings("unchecked")
    void failsToCreatesUserIfRequestIsInvalid() throws Exception {
        String identity = "W100000";
        when(userService.createUser(identity)).thenReturn(new User(identity, new Date(Long.valueOf("0"))));
        when(slackSigningSecretValidationService.validateRequest(any(HttpEntity.class))).thenReturn(false);

        mvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user_id", identity)
                .param("user_name", "Test"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @SuppressWarnings("unchecked")
    void isFoundIfUserAlreadyExist() throws Exception {
        String identity = "W100000";
        when(userService.createUser(identity)).thenThrow(DataIntegrityViolationException.class);
        when(slackSigningSecretValidationService.validateRequest(any(HttpEntity.class))).thenReturn(true);

        mvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("user_id", identity)
                .param("user_name", "Test"))
                .andExpect(status().isOk());
    }
}