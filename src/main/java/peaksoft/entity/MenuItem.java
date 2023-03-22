package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "menu_items")
@Getter @Setter
@NoArgsConstructor
public class MenuItem {
    @Id
    @SequenceGenerator(name = "menu_item_id_gen", sequenceName = "menu_item_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "menu_item_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    private boolean isVegetarian;
    @ManyToOne(cascade = {DETACH,PERSIST,MERGE,REFRESH})
    private SubCategory subCategory;
    @OneToOne(cascade = {ALL})
    private StopList stopList;
    @ManyToMany(cascade = {DETACH,MERGE,PERSIST,REFRESH}, mappedBy = "menuItems")
    private List<Cheque> cheques;
    @ManyToOne(cascade = {DETACH,MERGE,PERSIST,REFRESH})
    private Restaurant restaurant;

    public MenuItem(String name, String image, BigDecimal price, String description, boolean isVegetarian) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
