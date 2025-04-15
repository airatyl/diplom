package app.data;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginLogoutData {
    String operationName;
    String login;
}
