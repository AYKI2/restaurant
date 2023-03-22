package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter @Setter
@NoArgsConstructor
public class Category {
    @Id
    @SequenceGenerator(name = "category_id_gen", sequenceName = "category_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "category_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "category")
    private List<SubCategory> subCategories;

    public Category(String name) {
        this.name = name;
    }
}
