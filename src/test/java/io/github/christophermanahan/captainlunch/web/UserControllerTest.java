package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.model.User;
import io.github.christophermanahan.captainlunch.service.UserService;
import io.github.christophermanahan.captainlunch.service.ValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ValidationService validationService;

    @Test
    void createsUserIfRequestIsValid() throws Exception {
        String identity = "W100000";
        when(userService.createUser(identity)).thenReturn(new User(identity));
        when(validationService.validateRequest(any(HttpEntity.class))).thenReturn(true);

        mvc.perform(post("/join")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("user_id", identity)
            .param("user_name", "Test"))
            .andExpect(status().isCreated());
    }
}