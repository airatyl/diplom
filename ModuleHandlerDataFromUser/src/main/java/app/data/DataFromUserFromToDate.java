package app.data;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataFromUserFromToDate {
    private LocalDateTime from;
    private LocalDateTime to;
}
