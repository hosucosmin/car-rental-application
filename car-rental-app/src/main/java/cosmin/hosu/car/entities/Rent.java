package cosmin.hosu.car.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "RENT")
public class Rent {

    @Id
    @SequenceGenerator(
            name = "rent_sequence",
            sequenceName = "rent_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "rent_sequence"
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Car car;

    @Column(name = "RENT_EXT_ID")
    private String rentExtId;

    @Column(name = "DURATION")
    private double duration;

    @Column(name = "AMOUNT_DUE")
    private String amountDue;
}
