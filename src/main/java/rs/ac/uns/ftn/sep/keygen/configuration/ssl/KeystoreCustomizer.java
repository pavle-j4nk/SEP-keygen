package rs.ac.uns.ftn.sep.keygen.configuration.ssl;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import rs.ac.uns.ftn.sep.keygen.properties.KeygenProperties;
import rs.ac.uns.ftn.sep.keygen.service.keygen.KeygenService;

@Configuration
@RequiredArgsConstructor
public class KeystoreCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    private final KeygenService keygenService;
    private final KeygenProperties keygenProperties;

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.setSslStoreProvider(new KeygenSslStoreProvider(keygenService, keygenProperties));
    }

}
