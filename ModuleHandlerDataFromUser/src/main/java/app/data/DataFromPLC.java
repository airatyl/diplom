package app.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataFromPLC {
    private int sensorId;
    private float value;
    private int processID;



    public DataFromPLC(int sensorId, float value, int processID) {
        this.sensorId = sensorId;
        this.value = value;
        this.processID = processID;
    }

    public DataFromPLC(){}

    @Override
    public String toString() {
        return "DataFromPLC{" +
                "sensorId=" + sensorId +
                ", value=" + value +
                ", processID=" + processID +
                '}';
    }

}
