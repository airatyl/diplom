package app.consumers;

import app.data.DataFromPLC;
import app.data.DataProcess;
import app.data.DataToWebSocketGraph;
import app.entity.Paramoneachstage;
import app.entity.Sensorvalue;
import app.repository.MoldingstageRepository;
import app.repository.ParamoneachstageRepository;
import app.repository.SensorRepository;
import app.repository.SensorvalueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class KafkaListenerDataFromPLCHandler {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ParamoneachstageRepository paramoneachstageRepository;

    private final SensorvalueRepository sensorvalueRepository;

    private final SensorRepository sensorRepository;
    private final MoldingstageRepository moldingstageRepository;

    private int processID = 1;

    private long prevTimeOpl;
    private long prevTimeOsad;
    private long currentTime;

    //Получение данных из raw-data в data
    @KafkaListener(topics = "raw-data", groupId = "group2")
    public void listener(DataFromPLC data) {
        //Получение пороговых значений для датчика, который отправил данные и этапа литья из базы данных
        Paramoneachstage answer = paramoneachstageRepository
                .getParamoneachstageBySensor_AddressAndMoldingstage_Id(data.getAddress(), processID);
        //Создание объекта для отправки в Kafka
        DataToWebSocketGraph graph = new DataToWebSocketGraph();
        graph.setValue(data.getValue());
        graph.setParam(answer.getId().getControlparam());
        //сравнивание значения с пороговыми
        graph.setError(answer.getNeedcontrol() && (data.getValue() < answer.getMinvalue() || data.getValue() > answer.getMaxvalue()));
        //Отправка данных в топик Kafka data-to-websocket
        kafkaTemplate.send("data-to-websocket", graph);
        //Сохранение данных с датчика в базе данных

        sensorvalueRepository.save(new Sensorvalue(data.getValue()
                , Instant.now()
                , sensorRepository.getReferenceByAddress(data.getAddress())
                , graph.isError()
                , moldingstageRepository.getReferenceById(processID)));
    }

    @KafkaListener(topics = "process-id", groupId = "group3")
    public void timesCalculation(DataProcess process) {
        processID = process.getProcess();
        if (processID == 2) {
            prevTimeOpl = new Date().getTime();
        }
        if (processID == 3) {
            currentTime = new Date().getTime();
            prevTimeOsad = currentTime;
            Paramoneachstage answer = paramoneachstageRepository
                    .getParamoneachstageByMoldingstage_IdAndId_Controlparam(processID, "Время оплавления");
            DataToWebSocketGraph graph = new DataToWebSocketGraph();

            graph.setValue((currentTime - prevTimeOpl) / 1000F);
            graph.setParam("Время оплавления");
            //сравнивание значения с пороговыми
            graph.setError(answer.getNeedcontrol() && (currentTime - prevTimeOpl < answer.getMinvalue() || currentTime - prevTimeOpl > answer.getMaxvalue()));
            kafkaTemplate.send("data-to-websocket", graph);
            sensorvalueRepository.save(new Sensorvalue((float) process.getProcess()
                    , Instant.now()
                    , sensorRepository.getReferenceByAddress("add4")
                    , graph.isError(),moldingstageRepository.getReferenceById(processID)));
        }
        if (processID == 4) {
            currentTime = new Date().getTime();
            Paramoneachstage answer = paramoneachstageRepository
                    .getParamoneachstageByMoldingstage_IdAndId_Controlparam(processID, "Время осадки");
            DataToWebSocketGraph graph = new DataToWebSocketGraph();

            graph.setValue((currentTime - prevTimeOsad) / 1000F);
            graph.setParam("Время осадки");
            //сравнивание значения с пороговыми
            graph.setError(answer.getNeedcontrol() && (currentTime - prevTimeOsad < answer.getMinvalue() || currentTime - prevTimeOsad > answer.getMaxvalue()));
            kafkaTemplate.send("data-to-websocket", graph);
            sensorvalueRepository.save(new Sensorvalue((float) process.getProcess()
                    , Instant.now()
                    , sensorRepository.getReferenceByAddress("add5")
                    , graph.isError()
                    , moldingstageRepository.getReferenceById(processID)));
        }

    }
}
