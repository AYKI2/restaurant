package peaksoft.dto.response;

import java.util.List;

public record PaginationResponse(
        List<MenuItemResponse> menuItems,
        int currentPage,
        int size
) {
}
