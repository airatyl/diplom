package app.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DataToWebSocket {
    private int sensorId;
    private float value;
    private Date timestamp;
    private float minValue;
    private float maxValue;
    private int stage;
    private Optional<String> error;

    public DataToWebSocket(float value, Date timestamp, float minValue, float maxValue, int stage) {
        this.value = value;
        this.timestamp = timestamp;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stage = stage;
    }

    public DataToWebSocket(int sensorId, float value, Date timestamp, float minValue, float maxValue, int stage) {
        this.sensorId = sensorId;
        this.value = value;
        this.timestamp = timestamp;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "DataToWebSocket{" +
                "value=" + value +
                ", timestamp=" + timestamp +
                '}';
    }
}
