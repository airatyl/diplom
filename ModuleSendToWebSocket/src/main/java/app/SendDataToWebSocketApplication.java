package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.Socket;

@SpringBootApplication
public class SendDataToWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SendDataToWebSocketApplication.class, args);
    }

}
