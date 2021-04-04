package ro.dragomiralin.data.acquisition.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface AcquisitionService {
    void insert(String topic, MqttMessage message);
}
