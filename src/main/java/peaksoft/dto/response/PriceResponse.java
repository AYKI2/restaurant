package peaksoft.dto.response;

import java.math.BigDecimal;

public record PriceResponse(
        BigDecimal priceAverage,
        int service
) {
}
