package app.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DataFromPLC {
    private int sensorId;
    private double value;
    private int processID;

    @Override
    public String toString() {
        return "DataFromPLC{" +
                "sensorId=" + sensorId +
                ", value=" + value +
                ", processID=" + processID +
                '}';
    }

}
