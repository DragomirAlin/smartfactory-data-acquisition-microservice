package ro.dragomiralin.data.acquisition.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.configuration.MQTT;
import ro.dragomiralin.data.acquisition.model.Subscription;
import ro.dragomiralin.data.acquisition.service.AcquisitionService;
import ro.dragomiralin.data.acquisition.service.SubscribeService;
import ro.dragomiralin.data.acquisition.service.SubscriptionService;
import ro.dragomiralin.data.acquisition.service.exception.HttpError;

import javax.annotation.PostConstruct;


@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {
    private final SubscriptionService subscriptionService;
    private final AcquisitionService acquisitionService;
    private final MQTT mqtt;

    @PostConstruct
    public void subscribeAll() {
        subscriptionService.list().forEach(subscription -> {
            try {
                this.subscribeWithResponse(subscription);
            } catch (Exception e) {
                log.error("Subscribe failed: {}", e.getMessage());
            }
        });
    }

    public void unsubscribe(String topic) {
        subscriptionService.deleteByTopic(topic);
        try {
            mqtt.getClient().subscribeWithResponse(topic);
            log.info("Unsubscibe to: " + topic);
        } catch (Exception e) {
            log.error("An error occurred while unsubscribing to {}", topic, e);
            throw HttpError.badRequest(e.getMessage());
        }
    }

    @Override
    public void subscribe(String userId, String topic) {
        Subscription subscription = subscriptionService.create(Subscription.builder()
                .topic(topic)
                .userId(userId)
                .build());

        this.subscribeWithResponse(subscription);
    }

    private void subscribeWithResponse(Subscription subscription) {
        try {
            mqtt.getClient().subscribeWithResponse(subscription.getTopic(), (s, mqttMessage) -> acquisitionService.insert(subscription.getTopic(), mqttMessage));
        } catch (Exception e) {
            log.error("An error occurred while subscribing to {}.", subscription.getTopic(), e);
            throw HttpError.badRequest(e.getMessage());
        }
    }

}
