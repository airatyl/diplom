package app.consumers;

import app.data.DataFromWebsocket;
import app.entity.Operation;
import app.entity.Operationhistory;
import app.entity.Paramoneachstage;
import app.entity.User;
import app.repository.OperationRepository;
import app.repository.OperationhistoryRepository;
import app.repository.ParamoneachstageRepository;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListenerDataFromUserHandler {


    private final ParamoneachstageRepository paramoneachstageRepository;

    private final OperationhistoryRepository operationhistoryRepository;

    private final OperationRepository operationRepository;

    private final UserRepository userRepository;


    //Получение данных с топика Kafka data-to-save
    @KafkaListener(topics = "data-to-save", groupId = "group2" )
    public void listener(DataFromWebsocket data) {
        //Получение объекта который нужно изменить
        Paramoneachstage updatingData = paramoneachstageRepository
                .getParamoneachstageByMoldingstage_IdAndControlparam(data.getStage(), data.getParam());
        //Создание объекта с пользователем который совершил операцию и данными операции
        Operationhistory operationhistory =new Operationhistory();
        operationhistory.setData("минимальное значение с "+ updatingData.getMinvalue()+" на "+ data.getMinValue() +
                " максимальное с " + updatingData.getMaxvalue() +" на " + data.getMaxValue());
        Operation operation = operationRepository.getReferenceById(data.getStage());
        operationhistory.setOperation(operation);
        User user = userRepository.getReferenceById(1);
        operationhistory.setUserid(user);
        //Изменение пороговых значений
        updatingData.setMaxvalue(data.getMaxValue());
        updatingData.setMinvalue(data.getMinValue());
        updatingData.setNeedcontrol(data.isControl());
        //Сохранение изменений
        paramoneachstageRepository.save(updatingData);
        //Сохранение в историю операций
        operationhistoryRepository.save(operationhistory);

    }
}
