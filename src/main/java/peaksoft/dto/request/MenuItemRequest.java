package peaksoft.dto.request;

import java.math.BigDecimal;
import java.util.List;

public record MenuItemRequest(
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian,
        Long subCategoryId,
        Long stopList,
        List<Long> cheques
) {
}
