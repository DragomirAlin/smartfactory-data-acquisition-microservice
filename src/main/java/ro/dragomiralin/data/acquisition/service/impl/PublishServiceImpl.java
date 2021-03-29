package ro.dragomiralin.data.acquisition.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.configuration.MQTT;
import ro.dragomiralin.data.acquisition.model.Message;
import ro.dragomiralin.data.acquisition.service.PublishService;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishServiceImpl implements PublishService {
    private final MQTT mqtt;

    public void publish(Message message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getMessage().getBytes());
            mqttMessage.setQos(message.getQos());
            mqttMessage.setRetained(message.getRetained());
            mqtt.getClient().publish(message.getTopic(), mqttMessage);
        } catch (Exception e) {
            log.error("An error occurred while publishing a message on MQTT topic.", e);
        }
    }

}
