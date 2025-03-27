package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "paramoneachstage")
public class Paramoneachstage {
    @Id
    @ColumnDefault("nextval('paramoneachstage_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "controlparam", length = 45)
    private String controlparam;

    @Column(name = "unitofmeasurement", length = 10)
    private String unitofmeasurement;

    @Column(name = "minvalue")
    private Float minvalue;

    @Column(name = "maxvalue")
    private Float maxvalue;

    @Column(name = "needcontrol")
    private Boolean needcontrol;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor")
    private Sensor sensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moldingstage")
    private Moldingstage moldingstage;

}