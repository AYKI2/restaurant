package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "cheques")
@Getter @Setter
@NoArgsConstructor
public class Cheque {
    @Id
    @SequenceGenerator(name = "cheque_id_gen",sequenceName = "cheque_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "cheque_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private BigDecimal priceAverage;
    private LocalDate createdAt;
    @ManyToMany(cascade = {DETACH,MERGE,PERSIST,REFRESH})
    private List<MenuItem> menuItems;
    @ManyToOne(cascade = {DETACH,MERGE,PERSIST,REFRESH})
    private User employee;

    public Cheque(BigDecimal priceAverage, LocalDate createdAt) {
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
    }
}
