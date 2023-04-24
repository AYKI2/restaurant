package peaksoft.dto.request;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record MenuItemRequest(
        String name,
        String image,
        @Positive
        BigDecimal price,
        String description,
        boolean isVegetarian,
        Long subCategoryId,
        Long stopList,
        List<Long> cheques
) {
}
