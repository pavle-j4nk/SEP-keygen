package rs.ac.uns.ftn.sep.keygen.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keygen")
@Data
public class KeygenProperties {
    private String keyStore;
    private String keyStorePassword;
    private String caKeyAlias;
    private String caKeyPassword;
    private String commonName;
    private String organization;
    private String organizationalUnit;
    private String countryCode;
    private Long uid;
    private String token;
}
