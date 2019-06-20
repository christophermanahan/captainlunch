package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.configuration.ApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidationServiceTest {

    private ApplicationConfiguration applicationConfiguration;
    private ConfigurationService configurationService;
    private ValidationService validationService;

    private String signingSecret = "8f742231b10e8888abcd99yyyzzz85a5";
    private String requestBody = "token=xyzz0WbapA4vBCDEFasx0q6G&team_id=T1DC2JH3J&team_domain=testteamnow&channel_id=G8PSS9T3V&channel_name=foobar&user_id=U2CERLKJA&user_name=roadrunner&command=%2Fwebhook-collect&text=&response_url=https%3A%2F%2Fhooks.slack.com%2Fcommands%2FT1DC2JH3J%2F397700885554%2F96rGlfmibIGlgcZRskXaIFfN&trigger_id=398738663015.47445629121.803a0bc887a14d10d2c447fce8b6703c";
    private String timestamp = "1531420618";
    private String verificationSignature = "v0=a2114d57b48eac39b9ad189dd8316235a7b4a8d21a10bd27519666489c69b503";

    @BeforeEach
    void setUp() {
        applicationConfiguration = mock(ApplicationConfiguration.class);
        configurationService = mock(ConfigurationService.class);
        validationService = new ValidationService(configurationService);
    }

    @Test
    void isTrueWhenIncomingRequestIsValid() {
        when(applicationConfiguration.getIncomingRequestSigningSecret()).thenReturn(signingSecret);
        when(configurationService.getApplicationConfiguration()).thenReturn(applicationConfiguration);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Slack-Signature", verificationSignature);
        headers.add("X-Slack-Request-Timestamp", timestamp);
        HttpEntity request = new HttpEntity(requestBody, headers);

        Boolean valid = validationService.validateRequest(request);

        assertEquals(true, valid);
    }

    @Test
    void isFalseWhenIncomingRequestIsNotValid() {
        String invalidVerificationSignature = "invalid_verification_signature";
        when(applicationConfiguration.getIncomingRequestSigningSecret()).thenReturn(signingSecret);
        when(configurationService.getApplicationConfiguration()).thenReturn(applicationConfiguration);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Slack-Signature", invalidVerificationSignature);
        headers.add("X-Slack-Request-Timestamp", timestamp);
        HttpEntity request = new HttpEntity(requestBody, headers);

        Boolean valid = validationService.validateRequest(request);

        assertEquals(false, valid);
    }
}