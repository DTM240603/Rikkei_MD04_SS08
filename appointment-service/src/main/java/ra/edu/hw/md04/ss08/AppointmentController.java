package ra.edu.hw.md04.ss08;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final DoctorAvailabilityService doctorAvailabilityService;

    public AppointmentController(DoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> checkDoctorAvailability() {
        ApiResponse response = doctorAvailabilityService.checkOnCall();
        if (response instanceof ApiResponseError) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
