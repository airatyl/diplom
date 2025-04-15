package app.kafkaProducer;

import app.data.DataFromPLC;
import app.data.DataProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class Producer {
    int count =0;
    int process=1;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;
    //Отправка номера этапа
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void setProcess() {
        if(count==5){
            process=2;
            kafkaTemplate.send("process-id",new DataProcess(process));
        } else if (count==20) {
            process=3;
            kafkaTemplate.send("process-id",new DataProcess(process));
        }else if (count==22) {
            process=4;
            kafkaTemplate.send("process-id",new DataProcess(process));
        }else if (count==27) {
            process=0;
            kafkaTemplate.send("process-id",new DataProcess(process));
        }else if (count==37) {
            process=1;
            kafkaTemplate.send("process-id",new DataProcess(process));
        }
        count =count%37+1;
    }
    //Отправка параметра входное напряжение главной цепи
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void send1() {
        if(process==2){
        kafkaTemplate.send("raw-data",new DataFromPLC("add1",Math.random()*22+369));
        }
        else kafkaTemplate.send("raw-data",new DataFromPLC("add1",0));

    }
    //Отправка параметра вторичное напряжение
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void send2() {

        if(process==2){
            kafkaTemplate.send("raw-data",new DataFromPLC("add2",Math.random()*5.3+6.55));
        }
        else kafkaTemplate.send("raw-data",new DataFromPLC("add2",0));
    }
    //Отправка параметра усилие сжатия стыка
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void send3() {
        if(process==2){
            kafkaTemplate.send("raw-data",new DataFromPLC("add3",Math.random()*12+64));
        }
        else if(process==3){
            kafkaTemplate.send("raw-data",new DataFromPLC("add3",Math.random()*22+269));
        }
        else kafkaTemplate.send("raw-data",new DataFromPLC("add3",0));
    }
}
