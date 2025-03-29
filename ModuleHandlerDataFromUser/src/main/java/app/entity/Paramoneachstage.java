package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paramoneachstage")
public class Paramoneachstage {
    @EmbeddedId
    private ParamoneachstageId id;

    @MapsId("sensor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sensor", nullable = false)
    private Sensor sensor;

    @MapsId("moldingstage")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "moldingstage", nullable = false)
    private Moldingstage moldingstage;

    @Column(name = "controlparam", nullable = false, length = 45)
    private String controlparam;

    @Column(name = "unitofmeasurement", nullable = false, length = 10)
    private String unitofmeasurement;

    @Column(name = "minvalue", nullable = false)
    private Float minvalue;

    @Column(name = "maxvalue", nullable = false)
    private Float maxvalue;

    @Column(name = "needcontrol", nullable = false)
    private Boolean needcontrol = false;

}