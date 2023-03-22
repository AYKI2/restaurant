package peaksoft.dto.request;

import java.math.BigDecimal;

public record RestaurantRequest(
        String name,
        String location,
        String restType,
        BigDecimal service
) {
}
