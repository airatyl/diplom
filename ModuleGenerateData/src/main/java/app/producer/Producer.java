package app.producer;

import app.data.DataFromPLC;
import app.data.DataProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Producer {
    int process=1;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedDelay = 20, timeUnit = TimeUnit.SECONDS)
    public void setProcess() {
        process=process%4+1;
        kafkaTemplate.send("process-id",new DataProcess(process));
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void send1() {
        kafkaTemplate.send("raw-data",new DataFromPLC("add1",Math.random()*15+68));
    }
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void send2() {
        kafkaTemplate.send("raw-data",new DataFromPLC("add2",Math.random()*4+12));
    }
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void send3() {
        kafkaTemplate.send("raw-data",new DataFromPLC("add3",Math.random()*15+88));
    }
}
