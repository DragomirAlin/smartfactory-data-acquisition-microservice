package ro.dragomiralin.data.acquisition.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import ro.dragomiralin.data.acquisition.model.Data;

import java.util.List;

public interface AcquisitionService {
    void insert(String topic, MqttMessage message);
    List<Data> getDataByTopic(String topic);
    List<Data> getAll();
}
