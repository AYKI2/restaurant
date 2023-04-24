package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;


@Entity
@Table(name = "sub_categories")
@Getter
@Setter
@NoArgsConstructor
public class SubCategory {
    @Id
    @SequenceGenerator(name = "sub_category_id_gen", sequenceName = "sub_category_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "sub_category_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH})
    private Category category;
    @OneToMany(cascade = {ALL}, mappedBy = "subCategory")
    private List<MenuItem> menuItems = new ArrayList<>();
}
