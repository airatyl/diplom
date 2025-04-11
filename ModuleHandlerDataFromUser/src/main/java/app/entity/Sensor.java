package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensors", schema = "process_sch")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "place", nullable = false, length = 45)
    private String place;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensormodel", nullable = false)
    private Sensormodel sensormodel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensortype", nullable = false)
    private Sensortype sensortype;

    @Column(name = "address", nullable = false, length = 60)
    private String address;

}