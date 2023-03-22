package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "stop_lists")
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StopList {
    @Id
    @SequenceGenerator(name = "stop_list_id_gen",sequenceName = "stop_list_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "stop_list_id_gen", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String reason;
    private LocalDate date;
}
