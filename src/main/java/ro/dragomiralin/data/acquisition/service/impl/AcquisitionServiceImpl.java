package ro.dragomiralin.data.acquisition.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.common.Data;
import ro.dragomiralin.data.acquisition.common.Payload;
import ro.dragomiralin.data.acquisition.model.Pagination;
import ro.dragomiralin.data.acquisition.repository.DataRepository;
import ro.dragomiralin.data.acquisition.service.AcquisitionService;
import ro.dragomiralin.data.acquisition.service.SenderService;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AcquisitionServiceImpl implements AcquisitionService {
    private final DataRepository dataRepository;
    private final ObjectMapper objectMapper;
    private final SenderService senderService;

    @Override
    public void save(String topic, MqttMessage message) {
        try {
            var payload = objectMapper.readValue(message.getPayload(), Payload.class);
            var data = dataRepository.save(Data.builder()
                    .topic(topic)
                    .payload(payload.getPayload())
                    .metadata(payload.getMetadata())
                    .build());
            senderService.send(data);
        } catch (Exception e) {
            log.error("An error occurred while inserting data from broker.", e);
        }
    }

    @Override
    public Map<String, Object> getDataByTopic(String topic, Pagination pagination) {
        Pageable paging = PageRequest.of(pagination.getPage(), pagination.getSize());

        log.info("Get all data by topic={}.", topic);
        Page<Data> dataPage = dataRepository.findAllByTopic(topic, paging);
        return this.buildPaginationResponse(dataPage);
    }

    @Override
    public Map<String, Object> getAllData(Pagination pagination) {
        Pageable paging = PageRequest.of(pagination.getPage(), pagination.getSize());

        log.info("Get all data from database.");
        Page<Data> dataPage = dataRepository.findAll(paging);
        return this.buildPaginationResponse(dataPage);
    }

    private Map<String, Object> buildPaginationResponse(Page<Data> dataPage) {
        return Map.of("data", dataPage.getContent(), "currentPage", dataPage.getNumber(),
                "totalItems", dataPage.getTotalElements(), "totalPages", dataPage.getTotalPages());
    }
}
