package app.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataToWebSocketGraphics {
    float value;
    boolean isError;
    Date recordingTime;
    String param;
}
