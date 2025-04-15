package app.kafka.consumers;

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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private long prevTimeProst;
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
        //Сравнивание значения с пороговыми
        graph.setError(answer.getNeedcontrol() && (data.getValue() < answer.getMinvalue() || data.getValue() > answer.getMaxvalue()));
        //Отправка данных в топик Kafka data-to-websocket
        kafkaTemplate.send("data-to-websocket", graph);
        //Сохранение данных с датчика в базе данных
        sensorvalueRepository.save(new Sensorvalue(data.getValue()
                , LocalDateTime.now().toInstant(ZoneOffset.UTC)
                , sensorRepository.getReferenceByAddress(data.getAddress())
                , graph.isError()
                , moldingstageRepository.getReferenceById(processID)));
    }
    //Получение данных из process-id в process
    @KafkaListener(topics = "process-id", groupId = "group2")
    public void timesCalculation(DataProcess process) {
        //Получение текущего этапа
        processID = process.getProcess();
        if(processID==0){
            prevTimeProst=new Date().getTime();
        }
        if(processID==1){
            currentTime = new Date().getTime();
            //Создание объекта для отправки в Kafka
            DataToWebSocketGraph graph = new DataToWebSocketGraph();
            //Вычисление времени простоя
            graph.setValue((currentTime - prevTimeProst) / 1000F);
            graph.setParam("Время простоя");
            graph.setError(false);
            //Отправка данных в топик Kafka data-to-websocket
            kafkaTemplate.send("data-to-websocket", graph);
            //Сохранение в базу данных
            sensorvalueRepository.save(new Sensorvalue(graph.getValue()
                    , LocalDateTime.now().toInstant(ZoneOffset.UTC)
                    , sensorRepository.getReferenceByAddress("add6")
                    , graph.isError()
                    , moldingstageRepository.getReferenceById(processID)));
        }
        if (processID == 2) {
            prevTimeOpl = new Date().getTime();
        }
        if (processID == 3) {
            currentTime = new Date().getTime();
            prevTimeOsad = currentTime;
            //Получение пороговых значений для датчика, который отправил данные и этапа литья из базы данных
            Paramoneachstage answer = paramoneachstageRepository
                    .getParamoneachstageByMoldingstage_IdAndId_Controlparam(processID, "Время оплавления");
            //Создание объекта для отправки в Kafka
            DataToWebSocketGraph graph = new DataToWebSocketGraph();
            //Вычисление времени оплавления
            graph.setValue((currentTime - prevTimeOpl) / 1000F);
            graph.setParam("Время оплавления");
            //Сравнивание значения с пороговыми
            graph.setError(answer.getNeedcontrol() && (currentTime - prevTimeOpl < answer.getMinvalue() || currentTime - prevTimeOpl > answer.getMaxvalue()));
            //Отправка данных в топик Kafka data-to-websocket
            kafkaTemplate.send("data-to-websocket", graph);
            //Сохранение в базу данных
            sensorvalueRepository.save(new Sensorvalue(graph.getValue()
                    , LocalDateTime.now().toInstant(ZoneOffset.UTC)
                    , sensorRepository.getReferenceByAddress("add4")
                    , graph.isError(),moldingstageRepository.getReferenceById(processID)));
        }
        if (processID == 4) {
            currentTime = new Date().getTime();
            //Получение пороговых значений для датчика, который отправил данные и этапа литья из базы данных
            Paramoneachstage answer = paramoneachstageRepository
                    .getParamoneachstageByMoldingstage_IdAndId_Controlparam(processID, "Время осадки");
            //Создание объекта для отправки в Kafka
            DataToWebSocketGraph graph = new DataToWebSocketGraph();
            graph.setValue((currentTime - prevTimeOsad) / 1000F);
            graph.setParam("Время осадки");
            //Сравнивание значения с пороговыми
            graph.setError(answer.getNeedcontrol() && (currentTime - prevTimeOsad < answer.getMinvalue() || currentTime - prevTimeOsad > answer.getMaxvalue()));
            //Отправка данных в топик Kafka data-to-websocket
            kafkaTemplate.send("data-to-websocket", graph);
            //Сохранение в базу данных
            sensorvalueRepository.save(new Sensorvalue(graph.getValue()
                    , LocalDateTime.now().toInstant(ZoneOffset.UTC)
                    , sensorRepository.getReferenceByAddress("add5")
                    , graph.isError()
                    , moldingstageRepository.getReferenceById(processID)));
        }

    }
}
