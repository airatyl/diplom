package app.data;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DataToUserHistory {
    private String data;
    private String operationName;
    private String userLogin;
    private Date operationDate;
}
