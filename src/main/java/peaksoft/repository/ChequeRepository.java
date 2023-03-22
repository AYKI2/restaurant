package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.entity.Cheque;

import java.util.List;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("SELECT c FROM Cheque c join User u join Restaurant r where r.id = :restaurantId")
    List<Cheque> getAll(Long restaurantId);
}
