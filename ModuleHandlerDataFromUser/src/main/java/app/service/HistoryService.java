package app.service;

import app.data.DataFromUserFromToDate;
import app.data.DataToUserHistory;
import app.entity.Operationhistory;
import app.repository.OperationhistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final OperationhistoryRepository operationhistoryRepository;

    public List<DataToUserHistory> operationhistories(DataFromUserFromToDate data){
        List<DataToUserHistory> response = new ArrayList<>();
        List<Operationhistory> operationhistory = operationhistoryRepository.findAllByOperationDateBetween(data.getFrom().toInstant(ZoneOffset.UTC), data.getTo().toInstant(ZoneOffset.UTC));
        if(!operationhistory.isEmpty())
        for(Operationhistory o : operationhistory){
            response.add(new DataToUserHistory(o.getData(),o.getOperation().getName(),o.getUserid().getLogin(), Date.from(o.getOperationDate())));
        }
        return response;
    }
}
