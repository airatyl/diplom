package app.producer;

import org.apache.plc4x.java.api.PlcConnection;
import org.apache.plc4x.java.api.PlcDriverManager;
import org.apache.plc4x.java.api.messages.PlcReadRequest;
import org.apache.plc4x.java.api.messages.PlcReadResponse;
import org.apache.plc4x.java.api.messages.PlcSubscriptionRequest;
import org.apache.plc4x.java.api.messages.PlcSubscriptionResponse;
import org.apache.plc4x.java.api.model.PlcSubscriptionHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class SubscribingOverProfinet {
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public void subscribe(){
        //Подключение к ПЛК
        try (PlcConnection plcConnection = PlcDriverManager.getDefault().getConnectionManager().getConnection("адрес ПЛК")) {
            //Создание запроса этапа процесса
            PlcReadRequest.Builder builder1 = plcConnection.readRequestBuilder();
            builder1.addTagAddress("Этап процесса", "адрес параметра");
            PlcReadRequest readRequest = builder1.build();
            //Подписка на все датчики
            PlcSubscriptionRequest.Builder builder = plcConnection.subscriptionRequestBuilder();
            builder.addChangeOfStateTagAddress("Номер датчика", "адрес датчика");
            //...
            PlcSubscriptionRequest subscriptionRequest = builder.build();
            PlcSubscriptionResponse response = subscriptionRequest.execute().get();
            //Для каждого датчика получаем этап процесса и отправляем в Kafka
            for (String subscriptionName : response.getTagNames()) {
                final PlcSubscriptionHandle subscriptionHandle = response.getSubscriptionHandle(subscriptionName);
                subscriptionHandle.register(plcSubscriptionEvent -> {
                    for (String tagName : plcSubscriptionEvent.getTagNames()) {
                        System.out.println(plcSubscriptionEvent.getPlcValue(tagName));
                        try {
                            //Получение этапа процесса
                            PlcReadResponse response1 = readRequest.execute().get();
                            //Отправка в Kafka
                            kafkaTemplate.send("raw-data",
                                    new DataFromPLC(2,
                                            plcSubscriptionEvent.getPlcValue(tagName).getDouble(),
                                            response1.getInteger("Этап процесса")));
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
