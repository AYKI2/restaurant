package peaksoft.dto.request;

public record RestaurantRequest(
        String name,
        String location,
        String restType,
        int service
) {
}
