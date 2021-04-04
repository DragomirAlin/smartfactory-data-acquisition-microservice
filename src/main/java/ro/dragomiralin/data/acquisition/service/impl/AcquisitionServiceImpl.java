package ro.dragomiralin.data.acquisition.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.model.Data;
import ro.dragomiralin.data.acquisition.repository.DataRepository;
import ro.dragomiralin.data.acquisition.service.AcquisitionService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcquisitionServiceImpl implements AcquisitionService {
    private final DataRepository dataRepository;

    @Override
    public void insert(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        dataRepository.insert(Data.builder()
                .topic(topic)
                .payload(payload)
                .build());
    }

    @Override
    public List<Data> getDataByTopic(String topic) {
        return dataRepository.findAllByTopic(topic);
    }

    @Override
    public List<Data> getAll() {
        return dataRepository.findAll();
    }
}
