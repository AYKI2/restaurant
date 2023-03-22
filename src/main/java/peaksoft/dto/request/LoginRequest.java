package peaksoft.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
