package peaksoft.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalResponse {
    private List<CategoryResponse> categoryResponse;
    private List<ChequeResponse> chequeResponse;
    private List<MenuItemResponse> menuItemResponse;
    private List<RestaurantResponse> restaurantResponse;
    private List<StopListResponse> stopListResponse;
    private List<SubCategoryResponse> subCategoryResponse;
    private List<UserResponse> userResponse;
}
