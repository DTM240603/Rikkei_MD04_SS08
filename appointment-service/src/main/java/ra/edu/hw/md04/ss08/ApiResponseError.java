package ra.edu.hw.md04.ss08;

import java.time.Instant;

public record ApiResponseError(String message, int status, String error, Instant timestamp) implements ApiResponse {
}
