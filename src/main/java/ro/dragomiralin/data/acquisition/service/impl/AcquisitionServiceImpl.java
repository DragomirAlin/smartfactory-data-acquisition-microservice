package ro.dragomiralin.data.acquisition.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.model.Data;
import ro.dragomiralin.data.acquisition.repository.DataRepository;
import ro.dragomiralin.data.acquisition.service.AcquisitionService;
import ro.dragomiralin.data.acquisition.service.SenderService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcquisitionServiceImpl implements AcquisitionService {
    private final DataRepository dataRepository;
    private final ObjectMapper objectMapper;
    private final SenderService senderService;

    @Override
    public void insert(String topic, MqttMessage message) {
        try {
            Object payload = objectMapper.readValue(message.getPayload(), Object.class);
            Data data = dataRepository.save(Data.builder()
                    .topic(topic)
                    .payload(payload)
                    .build());
            senderService.send(data);
        } catch (Exception e) {
            log.error("An error occurred while inserting data from broker.", e);
        }
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
