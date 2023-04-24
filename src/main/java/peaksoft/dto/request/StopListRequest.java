package peaksoft.dto.request;

import java.time.LocalDate;

public record StopListRequest(
        String reason,

        LocalDate date,
        Long menuItemId
) {
}
