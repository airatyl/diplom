package app.websocket;


import app.data.DataToWebSocket;
import app.data.TestData2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class SendToWebsocket {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/tables")
    public void sendTableNames(String message) {
        TestData2 tableNames =new TestData2();
        messagingTemplate.convertAndSend("/topic/tableNames", tableNames);
    }


    @KafkaListener(topics = "data-to-websocket", groupId = "group1" )
    public void sending(DataToWebSocket data) {
        messagingTemplate.convertAndSend("/topic/data/" + data.getSensorId(), data);

    }
}
