package ra.edu.hw.md04.ss08;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @GetMapping("/{patientId}")
    public PatientInfoResponse getPatient(@PathVariable String patientId) {
        return new PatientInfoResponse(patientId, "Patient " + patientId);
    }
}
