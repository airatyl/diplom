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
    private double value;
    private Date timestamp;
    private int minValue;
    private int maxValue;
    private int stage;
    private Optional<String> error;

    public DataToWebSocket(double value, Date timestamp, int minValue, int maxValue, int stage) {
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
