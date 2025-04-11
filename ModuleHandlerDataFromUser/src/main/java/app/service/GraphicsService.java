package app.service;

import app.data.DataFromUserFromToDate;
import app.data.DataToWebSocketGraphics;
import app.entity.Paramoneachstage;
import app.entity.Sensorvalue;
import app.repository.ParamoneachstageRepository;
import app.repository.SensorvalueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        List<Sensorvalue> sensorvalues =sensorvalueRepository
                .findAllByRecordingtimeBetween(data.getFrom().toInstant(), data.getTo().toInstant());


        //Создание объекта для отправки в Kafka
        for(Sensorvalue sensor : sensorvalues){
            Paramoneachstage answer = paramoneachstageRepository
                    .getFirstBySensor_AddressAndMoldingstage_Id(sensor.getSensor().getAddress(),sensor.getMoldingstage().getId());
            DataToWebSocketGraphics graph= new DataToWebSocketGraphics();
            graph.setValue(sensor.getValue());
            graph.setRecordingTime(Date.from(sensor.getRecordingtime()));
            graph.setParam(answer.getId().getControlparam());
            //сравнивание значения с пороговыми
            graph.setError(answer.getNeedcontrol() && (sensor.getValue() < answer.getMinvalue() || sensor.getValue() > answer.getMaxvalue()));
            list.add(graph);
        }


        return list;
    }
}
