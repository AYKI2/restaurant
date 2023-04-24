package peaksoft.dto.request;

import java.util.List;

public record ChequeRequest(
        List<String> menuItems,
        Long userId
) {
}
