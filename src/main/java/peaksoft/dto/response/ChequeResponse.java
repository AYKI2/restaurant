package peaksoft.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ChequeResponse(
       String employee,
       List<String> menuItems,
       LocalDate createdAt,
       BigDecimal priceAverage,
       BigDecimal service,
       BigDecimal total
       )implements GlobalSearchResponse {
}
