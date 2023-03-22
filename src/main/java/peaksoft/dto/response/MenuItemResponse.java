package peaksoft.dto.response;

import peaksoft.entity.SubCategory;

import java.math.BigDecimal;

public record MenuItemResponse(
        String name,
        String image,
        BigDecimal price,
        String description,
        boolean isVegetarian,
        SubCategory subCategory
) {
}
