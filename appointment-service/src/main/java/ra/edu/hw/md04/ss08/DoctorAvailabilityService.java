package ra.edu.hw.md04.ss08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class DoctorAvailabilityService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorAvailabilityService.class);
    private static final String CIRCUIT_BREAKER_NAME = "doctorServiceCB";

    private final RestClient doctorRestClient;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    public DoctorAvailabilityService(
            RestClient doctorRestClient,
            CircuitBreakerFactory<?, ?> circuitBreakerFactory) {
        this.doctorRestClient = doctorRestClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    public DoctorAvailabilityResponse checkOnCall() {
        return circuitBreakerFactory.create(CIRCUIT_BREAKER_NAME).run(
                () -> doctorRestClient.get()
                        .uri("/api/doctors/on-call")
                        .retrieve()
                        .body(DoctorAvailabilityResponse.class),
                error -> {
                    logger.warn("Doctor service call failed: {}", error.toString());
                    return new DoctorAvailabilityResponse(false, "Doctor service is unavailable");
                });
    }
}
