package io.github.christophermanahan.captainlunch.web;

import io.github.christophermanahan.captainlunch.service.ManualRotationService;
import io.github.christophermanahan.captainlunch.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
class OverrideCaptainControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ValidationService validationService;

    @MockBean
    private ManualRotationService rotationService;

    @MockBean
    private Client client;

}