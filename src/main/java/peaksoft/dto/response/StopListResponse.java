package peaksoft.dto.response;

import java.time.LocalDate;

public record StopListResponse(
        Long id,
        String reason,
        LocalDate date,
        String menuItem
) implements GlobalSearchResponse{
}
