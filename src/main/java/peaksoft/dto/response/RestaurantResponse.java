package peaksoft.dto.response;

import peaksoft.entity.MenuItem;

import java.math.BigDecimal;
import java.util.List;

public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int numberOfEmployees,
        BigDecimal service,
        List<MenuItem> menuItems
) {
}
