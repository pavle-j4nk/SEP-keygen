package rs.ac.uns.ftn.sep.keygen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rs.ac.uns.ftn.sep.keygen.properties.KeygenProperties;

@SpringBootApplication
@EnableConfigurationProperties({KeygenProperties.class})
public class KeygenApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeygenApplication.class, args);
    }

}
