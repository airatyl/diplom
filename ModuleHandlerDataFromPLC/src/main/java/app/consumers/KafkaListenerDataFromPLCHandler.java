package app.consumers;

import app.data.DataFromPLC;
import app.data.DataToWebSocket;
import app.entity.Paramoneachstage;
import app.entity.Sensorvalue;
import app.repository.ParamoneachstageRepository;
import app.repository.SensorRepository;
import app.repository.SensorvalueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @KafkaListener(topics = "raw-data", groupId = "group2" )
    public void listener(DataFromPLC data) {
        Paramoneachstage answer = paramoneachstageRepository.getParamoneachstageBySensor_IdAndMoldingstage_Id(data.getSensorId(),data.getProcessID());
        DataToWebSocket sendingData =new DataToWebSocket(data.getSensorId(), data.getValue(), new Date(),answer.getMinvalue(),answer.getMaxvalue(), data.getProcessID());
        if (answer.getNeedcontrol() &&(data.getValue()<answer.getMinvalue() || data.getValue()>answer.getMaxvalue())){
            sendingData.setError(Optional.of("error"));
        }
        else sendingData.setError(Optional.empty());
        kafkaTemplate.send("data-to-websocket",sendingData);

        sensorvalueRepository.save(new Sensorvalue(data.getValue(),Instant.now(),sensorRepository.getReferenceById(data.getSensorId())));
        // answer =select from paramoneachstage where sensorId= data.sensorId and moldingstage= data.stageId
        //value = value -prevVlaue/time
        //data1 = new datatowebsocket(data.sensorId)
        //if data.value between min max ( setError(Optional.empty))
        //else set error
        //kafkaTemplate.send("data-to-websocket",data1)
        //data.save
    }
}
