package peaksoft.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ChequeRequest(
        BigDecimal priceAverage,
        LocalDate createdAt,
        List<String> menuItems,
        Long userId
) {
}
