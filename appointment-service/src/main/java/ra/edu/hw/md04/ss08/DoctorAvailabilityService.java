package ra.edu.hw.md04.ss08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class DoctorAvailabilityService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorAvailabilityService.class);
    private static final String FALLBACK_MESSAGE =
            "Hiện tại không thể kiểm tra thông tin bác sĩ, vui lòng thử lại sau vài giây.";

    private final RestClient doctorRestClient;

    public DoctorAvailabilityService(RestClient doctorRestClient) {
        this.doctorRestClient = doctorRestClient;
    }

    @CircuitBreaker(name = "doctorServiceCB", fallbackMethod = "getDoctorFallback")
    public ApiResponse checkOnCall() {
        return doctorRestClient.get()
                .uri("/api/doctors/on-call")
                .retrieve()
                .body(DoctorAvailabilityResponse.class);
    }

    public ApiResponseError getDoctorFallback(Exception exception) {
        logger.warn("Doctor service call failed: {}", exception.toString());
        return new ApiResponseError(
                FALLBACK_MESSAGE,
                503,
                "Doctor Service Error",
                java.time.Instant.now());
    }
}
