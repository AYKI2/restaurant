package peaksoft.dto.response;

import java.time.LocalDate;

public record StopListResponse(
        String reason,
        LocalDate date,
        MenuItemResponse menuItem
) {
}
