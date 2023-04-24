package peaksoft.dto.response;

import java.util.List;

public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployees,
        int service,
        List<MenuItemResponse> menuItems
) implements GlobalSearchResponse{
}
