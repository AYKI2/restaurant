package peaksoft.dto.response;

import peaksoft.entity.SubCategory;

import java.util.List;

public record CategoryResponse(
        Long id,
        String name,
        List<SubCategory> subCategories
) {
}
