package ra.edu.hw.md04.ss08;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @GetMapping("/on-call")
    public DoctorAvailabilityResponse checkOnCall() {
        return new DoctorAvailabilityResponse(true, "Doctor is on call");
    }
}
