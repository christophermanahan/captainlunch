package io.github.christophermanahan.captainlunch.service;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class SlackSigningSecretValidationService implements ValidationService {

    private ConfigurationService configurationService;

    @Autowired
    SlackSigningSecretValidationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public Boolean validateRequest(HttpEntity<String> request) {
        String verificationSignature = getVerificationSignature(request);
        String baseString = createBaseString(request);
        SecretKeySpec secretKey = createSecretKey();

        try {
            return createHash(baseString, secretKey).equals(verificationSignature);
        } catch (Exception ignored) {
            return false;
        }
    }

    private String createBaseString(HttpEntity<String> request) {
        String version = "v0";
        String timestamp = request.getHeaders().getFirst("X-Slack-Request-Timestamp");
        String body = request.getBody();
        return String.join(":", version, timestamp, body);
    }

    private String getVerificationSignature(HttpEntity<String> request) {
        return request.getHeaders().getFirst("X-Slack-Signature");
    }

    private SecretKeySpec createSecretKey() {
        String signingSecret = configurationService.getApplicationConfiguration().getIncomingRequestSigningSecret();
        return new SecretKeySpec(signingSecret.getBytes(), "HmacSHA256");
    }

    private String createHash(String baseString, SecretKeySpec secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        String signaturePrefix = "v0=";
        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(secretKey);
        return signaturePrefix + HexUtils.toHexString(sha256HMAC.doFinal(baseString.getBytes()));
    }
}
