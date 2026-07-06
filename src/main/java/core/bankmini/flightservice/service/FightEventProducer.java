package core.bankmini.flightservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FightEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String key, Object payload) {
        kafkaTemplate
                .send(topic, key, payload)
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        log.error(
                                "Failed to send message to topic {}",
                                topic,
                                exception
                        );
                        return;
                    }

                    log.info("Successfully sent message to topic: {}, partition: {}, offset: {}",
                            result.getRecordMetadata().topic(),
                            result.getProducerRecord().partition(),
                            result.getRecordMetadata().offset());
                });
    }
}
