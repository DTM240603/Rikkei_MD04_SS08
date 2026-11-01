package ra.edu.hw.md04.ss08;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class DoctorRestClientConfiguration {

    @Bean
    RestClient doctorRestClient(
            @Value("${services.doctor.base-url}") String doctorServiceBaseUrl) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(2));
        requestFactory.setReadTimeout(Duration.ofSeconds(2));

        return RestClient.builder()
                .baseUrl(doctorServiceBaseUrl)
                .requestFactory(requestFactory)
                .build();
    }
}
