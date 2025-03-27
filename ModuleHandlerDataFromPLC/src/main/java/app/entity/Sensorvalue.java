package app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "sensorvalue")
@AllArgsConstructor
@NoArgsConstructor
public class Sensorvalue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('sensorvalue_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "value")
    private Float value;

    @Column(name = "recordingtime")
    private Instant recordingtime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor")
    private Sensor sensor;


    public Sensorvalue(Float value, Instant recordingtime, Sensor sensor) {
        this.value = value;
        this.recordingtime = recordingtime;
        this.sensor = sensor;
    }
}