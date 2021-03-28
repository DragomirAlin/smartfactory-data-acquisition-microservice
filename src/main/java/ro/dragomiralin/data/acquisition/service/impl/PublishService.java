package ro.dragomiralin.data.acquisition.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.configuration.MQTT;
import ro.dragomiralin.data.acquisition.model.PublishDTO;

@Slf4j
@Service
public class PublishService {

    public void publishMessage(PublishDTO publishObject) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage(publishObject.getMessage().getBytes());
        mqttMessage.setQos(publishObject.getQos());
        mqttMessage.setRetained(publishObject.getRetained());
        MQTT.getClient().publish(publishObject.getTopic(), mqttMessage);
    }
}

