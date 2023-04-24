package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.StopList;

import java.time.LocalDate;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    StopList findByDate(LocalDate date);

}
