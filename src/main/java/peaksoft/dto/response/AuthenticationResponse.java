package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String username,
        String token
) {
}
