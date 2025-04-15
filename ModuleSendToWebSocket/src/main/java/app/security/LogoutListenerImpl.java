package app.security;

import app.data.LoginLogoutData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class LogoutListenerImpl implements
        ApplicationListener<LogoutSuccessEvent> {

    @Override
    public void onApplicationEvent(LogoutSuccessEvent appEvent) {
        UserDetails userDetails = (UserDetails) appEvent.getAuthentication().getPrincipal();
        if (userDetails != null) {
            LoginLogoutData data = new LoginLogoutData();
            data.setOperationName("Выход");
            data.setLogin(userDetails.getUsername());
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = null;
            try {
                request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8084/home/save"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                        .build();
                HttpResponse<String> response = client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}