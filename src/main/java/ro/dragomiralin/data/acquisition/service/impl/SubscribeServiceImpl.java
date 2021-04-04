package ro.dragomiralin.data.acquisition.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.configuration.MQTT;
import ro.dragomiralin.data.acquisition.service.SubscribeService;
import ro.dragomiralin.data.acquisition.service.exception.HttpError;


@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {
    private final MQTT mqtt;

    public void unsubscribe(String userId, String topic) {
        try {
            mqtt.getClient().subscribeWithResponse(topic);
            log.info("Unsubscibe to: " + topic);
        } catch (Exception e) {
            log.error("An error occurred while unsubscribing to {}", topic, e);
            throw HttpError.badRequest(e.getMessage());
        }
    }

    @Override
    public void subscribe(String sub, String topic) {

    }

    private String subscribeWithResponse(String topic) {
        try {
            mqtt.getClient().subscribeWithResponse(topic, (s, mqttMessage) -> {
                String message = new String(mqttMessage.getPayload());
                log.info("Message from MQTT: " + message + ", topic: " + topic); // Listener

            });
        } catch (Exception e) {
            log.error("An error occurred while subscribing to {}.", topic, e);
            throw HttpError.badRequest(e.getMessage());
        }
        return "Success";
    }

}
