package app;

import app.data.DataFromUserFromToDate;
import app.data.DataFromUserChange;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/home")
public class MainPage {
    @GetMapping("/main")
    public String mainPage(){
        return "pageWithGraphics";
    }
    @GetMapping("/change")
    public String changePage(){
        return "change";
    }
    @GetMapping("/graphics")
    public String graphicsPage(){
        return "graphics";
    }
    @GetMapping("/history")
    public String historyPage(){
        return "history";
    }
    @GetMapping("/login")
    public String login(){
        return "auth";
    }
    @PostMapping("/change")
    public @ResponseBody String change(@RequestBody DataFromUserChange data, Authentication authentication) throws IOException, InterruptedException {
        data.setLogin(authentication.getName());
        ObjectMapper mapper =new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/home/change"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        return  response.body();
    }
    @PostMapping(path = "/history",
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody String history(@RequestBody DataFromUserFromToDate data) throws IOException, InterruptedException {
        ObjectMapper mapper =new ObjectMapper();
        mapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/home/history"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        return  response.body();
    }

    @PostMapping(path = "/graphics",
            consumes = "application/json",
            produces = "application/json")
    public @ResponseBody String graphics(@RequestBody DataFromUserFromToDate data) throws IOException, InterruptedException {
        ObjectMapper mapper =new ObjectMapper();
        mapper.findAndRegisterModules();
        System.out.println(data.getFrom());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8084/home/graphics"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(data)))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        return  response.body();
    }

}
