package app.websocket;


import app.data.DataFromHandler;
import app.data.DataFromWebsocket;
import app.data.DataToWebSocketGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SendToWebsocket {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/change")
    public void sendTableNames(DataFromWebsocket message) {
        kafkaTemplate.send("data-to-save",message);
    }


    @KafkaListener(topics = "data-to-websocket", groupId = "group1" )
    public void sending(DataFromHandler data) {
        DataToWebSocketGraph data1 =new DataToWebSocketGraph(data.getValue(), data.isError());
        messagingTemplate.convertAndSend("/topic/data/" + data.getId(), data1);

    }
}
