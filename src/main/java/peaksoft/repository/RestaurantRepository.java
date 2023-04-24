package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
