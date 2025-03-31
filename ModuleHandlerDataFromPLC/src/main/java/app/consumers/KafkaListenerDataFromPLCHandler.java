package app.consumers;

import app.data.DataFromPLC;
import app.data.DataToWebSocket;
import app.data.DataToWebSocketGraph;
import app.entity.Paramoneachstage;
import app.entity.Sensorvalue;
import app.repository.ParamoneachstageRepository;
import app.repository.SensorRepository;
import app.repository.SensorvalueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KafkaListenerDataFromPLCHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ParamoneachstageRepository paramoneachstageRepository;

    private final SensorvalueRepository sensorvalueRepository;

    private final SensorRepository sensorRepository;

    //Получение данных из raw-data в data
    @KafkaListener(topics = "raw-data", groupId = "group2" )
    public void listener(DataFromPLC data) {
        //Получение пороговых значений для датчика, который отправил данные и этапа литья из базы данных
        Paramoneachstage answer = paramoneachstageRepository
                .getParamoneachstageBySensor_IdAndMoldingstage_Id(data.getSensorId(),data.getProcessID());
        //Создание объекта для отправки в Kafka
        DataToWebSocketGraph graph= new DataToWebSocketGraph();
        graph.setValue(data.getValue());
        graph.setId(data.getSensorId());
        //сравнивание значения с пороговыми
        graph.setError(answer.getNeedcontrol() && (data.getValue() < answer.getMinvalue() || data.getValue() > answer.getMaxvalue()));
        //Отправка данных в топик Kafka data-to-websocket
        kafkaTemplate.send("data-to-websocket",graph);
        //Сохранение данных с датчика в базе данных
        sensorvalueRepository.save(new Sensorvalue(data.getValue(),Instant.now(),sensorRepository.getReferenceById(data.getSensorId())));
    }
}
