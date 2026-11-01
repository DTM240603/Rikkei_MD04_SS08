package ra.edu.hw.md04.ss08;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.ResourceAccessException;

import io.github.resilience4j.retry.annotation.Retry;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private static final String FALLBACK_MESSAGE =
            "Hiện tại không thể lấy thông tin bệnh nhân, vui lòng thử lại sau vài giây.";

    private final RestClient patientRestClient;

    public PatientService(RestClient patientRestClient) {
        this.patientRestClient = patientRestClient;
    }

    @Retry(name = "patientRetry", fallbackMethod = "getPatientFallback")
    public ApiResponse getPatient(String patientId) {
        return patientRestClient.get()
                .uri("/api/patients/{patientId}", patientId)
                .retrieve()
                .body(PatientInfoResponse.class);
    }

    public ApiResponseError getPatientFallback(String patientId, ResourceAccessException exception) {
        logger.warn("Patient service call failed for patient {}: {}", patientId, exception.toString());
        return new ApiResponseError(FALLBACK_MESSAGE, 503, "Patient Service Error", Instant.now());
    }
}
