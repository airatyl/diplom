package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "sensors")
public class Sensor {
    @Id
    @ColumnDefault("nextval('sensors_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "place", length = 45)
    private String place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensormodel")
    private Sensormodel sensormodel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensortype")
    private Sensortype sensortype;

}