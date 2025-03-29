package app.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataFromHandler {
    int id;
    float value;
    boolean isError;
}
