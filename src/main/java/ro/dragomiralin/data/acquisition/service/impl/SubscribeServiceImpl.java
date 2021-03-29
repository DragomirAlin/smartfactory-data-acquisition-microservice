package ro.dragomiralin.data.acquisition.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.configuration.MQTT;
import ro.dragomiralin.data.acquisition.service.SubscribeService;
import ro.dragomiralin.data.acquisition.service.exception.HttpError;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class SubscribeServiceImpl implements SubscribeService {

    public String subscribeWithResponse(String topic) {
        try {
            AtomicReference<String> message = null;
            MQTT.getClient().subscribeWithResponse(topic, (s, mqttMessage) -> {
                message.set(String.valueOf(mqttMessage.getPayload()));

                log.info("Message from MQTT: " + message + ", topic: " + topic);
            });
            return message.get();
        } catch (Exception e) {
            log.error("An error occurred while subscribing to {}.", topic, e);
            throw HttpError.badRequest(e.getMessage());
        }
    }

    public void unsubscribe(String topic) {
        try {
            MQTT.getClient().subscribeWithResponse(topic);
            log.info("Unsubscibe to: " + topic);
        } catch (Exception e) {
            log.error("An error occurred while unsubscribing to {}", topic, e);
            throw HttpError.badRequest(e.getMessage());
        }
    }

}
