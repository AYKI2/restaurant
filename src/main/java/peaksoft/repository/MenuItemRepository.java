package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.GlobalSearchResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory.name) FROM MenuItem m join Restaurant r where r.id = :restaurantId")
    List<MenuItemResponse> getAll(Long restaurantId);
    @Query("SELECT m FROM MenuItem m where m.restaurant.id = :restaurantId order by m.price asc")
    List<MenuItem> getAllAsc(Long restaurantId);
    @Query("SELECT m FROM MenuItem m where m.restaurant.id = :restaurantId order by m.price desc ")
    List<MenuItem> getAllDesc(Long restaurantId);
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory.name) FROM MenuItem m join Restaurant r where m.id = :menItemId and r.id = :restaurantId")
    Optional<MenuItemResponse> getMenuItemById(Long restaurantId, Long menItemId);
    Optional<MenuItem> findByName(String name);
    Optional<MenuItem> findByStopListId(Long id);
    Optional<MenuItem> findByStopList(StopList stopList);
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory.name) " +
            "FROM MenuItem m join m.subCategory s join m.subCategory.category c where c.name ilike concat('%',:word,'%') or s.name ilike concat('%',:word,'%') or m.name ilike concat('%',:word,'%')")
    List<GlobalSearchResponse> globalSearch(String word);
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory.name) FROM MenuItem m where m.isVegetarian = :isVegetarian")
    List<MenuItemResponse> getAllByVegetarian(Boolean isVegetarian);
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory.name) FROM MenuItem m")
    Page<MenuItemResponse> findAllBy(Pageable pageable);
}
