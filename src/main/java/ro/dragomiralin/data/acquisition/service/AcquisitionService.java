package ro.dragomiralin.data.acquisition.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import ro.dragomiralin.data.acquisition.common.Data;

import java.util.List;
import java.util.Map;

public interface AcquisitionService {
    void save(String topic, MqttMessage message);
    List<Data> getDataByTopic(String topic);
    Map<String, Object> getAllData(int page, int size);
}
