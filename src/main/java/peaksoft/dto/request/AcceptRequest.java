package peaksoft.dto.request;

public record AcceptRequest(
        Long userId,
        Long restaurantId,
        boolean isAccept
) {
}
