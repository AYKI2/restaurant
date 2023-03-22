package peaksoft.dto.response;

import peaksoft.entity.Category;
import peaksoft.entity.MenuItem;

import java.util.List;

public record SubCategoryResponse(
        String name,
        List<MenuItem> menuItems,
        Category category
) {
}
