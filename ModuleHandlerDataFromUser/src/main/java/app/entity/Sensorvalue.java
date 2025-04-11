package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sensorvalue", schema = "process_sch")
public class Sensorvalue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value", nullable = false)
    private Float value;

    @Column(name = "recordingtime", nullable = false)
    private Instant recordingtime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor", nullable = false)
    private Sensor sensor;

    @Column(name = "error", nullable = false)
    private Boolean error = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "moldingstage", nullable = false)
    private Moldingstage moldingstage;

}