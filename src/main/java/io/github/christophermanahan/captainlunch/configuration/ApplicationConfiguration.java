package io.github.christophermanahan.captainlunch.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class ApplicationConfiguration {

    private String incomingRequestSigningSecret;

    public String getIncomingRequestSigningSecret() {
        return incomingRequestSigningSecret;
    }

    public void setIncomingRequestSigningSecret(String incomingRequestSigningSecret) {
        this.incomingRequestSigningSecret = incomingRequestSigningSecret;
    }
}
