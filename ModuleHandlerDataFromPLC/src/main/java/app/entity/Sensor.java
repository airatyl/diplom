package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "sensors", schema = "process_sch")
public class Sensor {
    @Id
    @ColumnDefault("nextval('process_sch.sensors_id_seq'::regclass)")
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

    @ColumnDefault("'no address'")
    @Column(name = "address", nullable = false, length = 60)
    private String address;

}