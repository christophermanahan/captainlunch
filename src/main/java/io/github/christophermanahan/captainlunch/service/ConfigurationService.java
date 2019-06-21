package io.github.christophermanahan.captainlunch.service;

import io.github.christophermanahan.captainlunch.configuration.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {

    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    public ConfigurationService(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }
}
