package io.github.christophermanahan.captainlunch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class ApplicationProperties implements SigningSecretConfiguration, OutgoingRequestConfiguration {

    private String incomingRequestSigningSecret;
    private String authToken;
    private String notifyUsersURI;

    public String getIncomingRequestSigningSecret() {
        return incomingRequestSigningSecret;
    }

    public void setIncomingRequestSigningSecret(String incomingRequestSigningSecret) {
        this.incomingRequestSigningSecret = incomingRequestSigningSecret;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getNotifyUsersURI() {
        return notifyUsersURI;
    }

    public void setNotifyUsersURI(String notifyUsersURI) {
        this.notifyUsersURI = notifyUsersURI;
    }
}
