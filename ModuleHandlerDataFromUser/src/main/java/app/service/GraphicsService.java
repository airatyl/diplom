package app.service;

import app.data.DataFromUserFromToDate;
import app.data.DataToWebSocketGraphics;
import app.entity.Paramoneachstage;
import app.entity.Sensorvalue;
import app.repository.ParamoneachstageRepository;
import app.repository.SensorvalueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphicsService {
    private final SensorvalueRepository sensorvalueRepository;
    private final ParamoneachstageRepository paramoneachstageRepository;

    public List<DataToWebSocketGraphics> getGraphics(DataFromUserFromToDate data) {
        List<DataToWebSocketGraphics> list = new ArrayList<>();
        System.out.println(data.getFrom().toInstant(ZoneOffset.UTC));

        List<Sensorvalue> sensorvalues =sensorvalueRepository
                .findAllByRecordingtimeBetween(data.getFrom().toInstant(ZoneOffset.UTC), data.getTo().toInstant(ZoneOffset.UTC));


        //Создание объекта для отправки в Kafka
        String controlParam="";
        for(Sensorvalue sensor : sensorvalues){
            controlParam = switch (sensor.getSensor().getAddress()) {
                case "add1" -> "Входное напряжение главной цепи, В";
                case "add2" -> "Вторичное напряжение, В";
                case "add3" -> "Усилие сжатия стыка, кН";
                case "add4" -> "Время оплавления, с";
                case "add5" -> "Время осадки, с";
                case "add6" -> "Время простоя, с";
                default -> controlParam;
            };
            DataToWebSocketGraphics graph= new DataToWebSocketGraphics();
            graph.setValue(sensor.getValue());
            graph.setRecordingTime(Date.from(sensor.getRecordingtime()));
            graph.setParam(controlParam);
            //сравнивание значения с пороговыми
            graph.setError(sensor.getError());
            list.add(graph);
        }


        return list;
    }
}
