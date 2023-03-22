package peaksoft.dto.response;

import peaksoft.entity.MenuItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ChequeResponse(
        String employee,
        List<MenuItem> menuItems,
        LocalDate createdAt,
        BigDecimal priceAverage,
        BigDecimal service,
        BigDecimal total
) {
}
