package app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "paramoneachstage", schema = "process_sch")
public class Paramoneachstage {
    @EmbeddedId
    private ParamoneachstageId id;

    @MapsId("moldingstage")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "moldingstage", nullable = false)
    private Moldingstage moldingstage;

    @Column(name = "unitofmeasurement", nullable = false, length = 10)
    private String unitofmeasurement;

    @Column(name = "minvalue", nullable = false)
    private Float minvalue;

    @Column(name = "maxvalue", nullable = false)
    private Float maxvalue;

    @Column(name = "needcontrol", nullable = false)
    private Boolean needcontrol = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor")
    private Sensor sensor;

    public String getControlChanged(){
        if(needcontrol){
            return "да";
        }
        return "нет";
    }
}