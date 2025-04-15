package app.websocket;

import app.data.DataFromHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendToWebsocket {
    private final SimpMessagingTemplate messagingTemplate;
    //Получение данных с топика data-to-websocket в data
    @KafkaListener(topics = "data-to-websocket", groupId = "group1" )
    public void sending(DataFromHandler data) {;
        //Отправка данных по WebSocket
        messagingTemplate.convertAndSend("/topic/data", data);

    }
}
