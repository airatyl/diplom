package app.websocket;


import app.data.DataFromHandler;
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

//    @MessageMapping("/change")
//    public void sendTableNames(DataFromWebsocket message) {
//        //Отправка данных в топик data-to-save
//        kafkaTemplate.send("data-to-save",message);
//    }
    //Получение данных с топика data-to-websocket в data
    @KafkaListener(topics = "data-to-websocket", groupId = "group1" )
    public void sending(DataFromHandler data) {
        //Создаем объект для отправки по WebSocket
//        DataToWebSocketGraph data1 =new DataToWebSocketGraph(data.getValue(), data.isError());
        //Отправка данных по WebSocket
        messagingTemplate.convertAndSend("/topic/data", data);

    }
}
