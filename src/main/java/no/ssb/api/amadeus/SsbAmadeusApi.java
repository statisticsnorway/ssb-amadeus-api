package no.ssb.api.amadeus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by lrb on 06.02.2017.
 */
@SpringBootApplication
public class SsbAmadeusApi {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SsbAmadeusApi.class);
        app.run();
    }
}
