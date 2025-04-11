package app.service;

import app.data.DataFromUserChange;
import app.entity.Operation;
import app.entity.Operationhistory;
import app.entity.Paramoneachstage;
import app.entity.User;
import app.repository.OperationRepository;
import app.repository.OperationhistoryRepository;
import app.repository.ParamoneachstageRepository;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ChangeService {
    private final ParamoneachstageRepository paramoneachstageRepository;

    private final OperationhistoryRepository operationhistoryRepository;

    private final OperationRepository operationRepository;

    private final UserRepository userRepository;



    public String changeMinMax(DataFromUserChange data) {
        //Получение объекта который нужно изменить
        Paramoneachstage updatingData = paramoneachstageRepository
                .getParamoneachstageByMoldingstage_IdAndId_Controlparam(data.getStage(), data.getParam());
        if(updatingData==null){
            return "error";
        }
        //Создание объекта с пользователем который совершил операцию и данными операции
        Operationhistory operationhistory =new Operationhistory();
        operationhistory.setData("Параметр:"+data.getParam()
                +"Этап"+updatingData.getMoldingstage().getName()
                +"Минимальное значение:"+ updatingData.getMinvalue()+"->"+ data.getMinValue()
                +" Максимальное:" + updatingData.getMaxvalue() +"->" + data.getMaxValue()
                +" Контроль:"+updatingData.getControlChanged()+"->"+data.getControlChanged());
        Operation operation = operationRepository.getOperationByName("Изменение пороговых значений");
        operationhistory.setOperation(operation);
        User user = userRepository.getUserByLogin(data.getLogin());
        operationhistory.setUserid(user);
        operationhistory.setOperationDate(Instant.now());
        //Изменение пороговых значений
        updatingData.setMaxvalue(data.getMaxValue());
        updatingData.setMinvalue(data.getMinValue());
        updatingData.setNeedcontrol(data.isControl());
        //Сохранение изменений
        paramoneachstageRepository.save(updatingData);
        //Сохранение в историю операций
        operationhistoryRepository.save(operationhistory);
        return "OK";

    }
}
