package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findByName(String name);
    List<SubCategory> findByCategoryId(Long categoryId);
    @Query("SELECT new peaksoft.dto.response.SubCategoryResponse(s.id,s.name,s.category.name) FROM SubCategory s")
    List<SubCategoryResponse> getAll();
    @Query("SELECT new peaksoft.dto.response.SubCategoryResponse(s.id,s.name,s.category.name) FROM SubCategory s where s.category.id = ?1 group by s.category.name order by s.name asc")
    List<SubCategoryResponse> getAllByCategoryIdAsc(Long categoryId);
    @Query("SELECT new peaksoft.dto.response.SubCategoryResponse(s.id,s.name,s.category.name) FROM SubCategory s where s.category.id = ?1 group by s.category.name order by s.name desc")
    List<SubCategoryResponse> getAllByCategoryIdDesc(Long categoryId);
}
