package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.PriceResponse;
import peaksoft.entity.Cheque;

import java.util.List;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("SELECT new peaksoft.dto.response.PriceResponse(c.priceAverage, c.employee.restaurant.service) FROM Cheque c where c.employee.restaurant.id = ?1")
    PriceResponse getAveragePrice(Long restaurantId);
    @Query("SELECT c FROM Cheque c where c.employee.restaurant.id = ?1")
    List<Cheque> getAll(Long restaurantId);
}
