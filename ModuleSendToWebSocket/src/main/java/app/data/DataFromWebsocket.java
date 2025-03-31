package app.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataFromWebsocket {
    private String param;
    private int stage;
    private boolean control;
    private float minValue;
    private float maxValue;


}
