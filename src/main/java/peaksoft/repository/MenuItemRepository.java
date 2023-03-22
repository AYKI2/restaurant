package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory) FROM MenuItem m join Restaurant r where r.id = :restaurantId")
    List<MenuItemResponse> getAll(Long restaurantId);
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory) FROM MenuItem m join Restaurant r where m.id = :menItemId and r.id = :restaurantId")
    Optional<MenuItemResponse> getMenuItemById(Long restaurantId, Long menItemId);
    Optional<MenuItem> findByName(String name);
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory) FROM MenuItem  m join  m.subCategory s join s.category c where (m.name ILIKE %:word% OR c.name ILIKE %:word% OR s.name ILIKE %:word%)")
    List<MenuItemResponse> globalSearch(String word);
    @Query("SELECT new peaksoft.dto.response.MenuItemResponse(m.name,m.image,m.price,m.description,m.isVegetarian,m.subCategory) FROM MenuItem m where m.isVegetarian = :isVegetarian")
    List<MenuItemResponse> getAll(Boolean isVegetarian);
}
