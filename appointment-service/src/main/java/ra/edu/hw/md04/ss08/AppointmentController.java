package ra.edu.hw.md04.ss08;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final DoctorAvailabilityService doctorAvailabilityService;
    private final PatientService patientService;

    public AppointmentController(
            DoctorAvailabilityService doctorAvailabilityService,
            PatientService patientService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> checkDoctorAvailability() {
        ApiResponse response = doctorAvailabilityService.checkOnCall();
        if (response instanceof ApiResponseError) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patients/{patientId}")
    public ResponseEntity<ApiResponse> getPatient(@PathVariable String patientId) {
        ApiResponse response = patientService.getPatient(patientId);
        if (response instanceof ApiResponseError) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
