package ro.dragomiralin.data.acquisition.service;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import ro.dragomiralin.data.acquisition.common.Data;
import ro.dragomiralin.data.acquisition.model.Pagination;

import java.util.List;
import java.util.Map;

public interface AcquisitionService {
    void save(String topic, MqttMessage message);
    Map<String, Object> getDataByTopic(String topic, Pagination pagination);
    Map<String, Object> getAllData(Pagination pagination);
    List<Data> searchData(String textSearch);
}
