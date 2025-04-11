package app.data;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataFromUserFromToDate {
    private Date from;
    private Date to;
}
