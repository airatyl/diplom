package app.service;

import app.entity.Operation;
import app.entity.Operationhistory;
import app.entity.User;
import app.repository.OperationRepository;
import app.repository.OperationhistoryRepository;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class SaveService {

    private final OperationRepository operationRepository;
    private final OperationhistoryRepository operationhistoryRepository;
    private final UserRepository userRepository;

    public void saveOperationHistory(String operationName, String login){
        Operationhistory operationhistory =new Operationhistory();
        operationhistory.setData("Пользователь выполнил "+operationName);
        Operation operation = operationRepository.getOperationByName(operationName);
        operationhistory.setOperation(operation);
        User user = userRepository.getUserByLogin(login);
        operationhistory.setUserid(user);
        operationhistory.setOperationDate(Instant.now());
        operationhistoryRepository.save(operationhistory);
    }
}
