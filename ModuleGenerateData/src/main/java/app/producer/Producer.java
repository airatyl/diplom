package app.producer;

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
        process=process%5+1;
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void send1() {
        kafkaTemplate.send("raw-data",new DataFromPLC(1,Math.random()*280,process));
    }
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void send2() {
        kafkaTemplate.send("raw-data",new DataFromPLC(2,Math.random()*280,process));
    }
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void send3() {
        kafkaTemplate.send("raw-data",new DataFromPLC(3,Math.random()*280,process));
    }
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void send4() {
        kafkaTemplate.send("raw-data",new DataFromPLC(4,Math.random()*280,process));
    }
    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.SECONDS)
    public void send5() {
        kafkaTemplate.send("raw-data",new DataFromPLC(5,Math.random()*280,process));
    }

}
