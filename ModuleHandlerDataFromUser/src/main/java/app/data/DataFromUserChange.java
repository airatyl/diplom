package app.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataFromUserChange {
    private String param;
    private int stage;
    private boolean control;
    private float minValue;
    private float maxValue;
    private String login;

    public String getControlChanged(){
        if(control){
            return "да";
        }
        return "нет";
    }
}
