package aem.example.tdd.ecasastorage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ecasa")
public class EcasaProperties {

    private final Security security = new Security();

    public Security getSecurity() {
        return security;
    }

    public static class Security{
        private String secret = "secret";
        private long tokenValidityInSeconds = 1800; // 30 min

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public long getTokenValidityInSeconds() {
            return tokenValidityInSeconds;
        }

        public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
            this.tokenValidityInSeconds = tokenValidityInSeconds;
        }
    }
}
