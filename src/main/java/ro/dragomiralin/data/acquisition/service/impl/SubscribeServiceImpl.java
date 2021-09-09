package ro.dragomiralin.data.acquisition.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.configuration.MQTTClient;
import ro.dragomiralin.data.acquisition.model.Subscription;
import ro.dragomiralin.data.acquisition.service.AcquisitionService;
import ro.dragomiralin.data.acquisition.service.SubscribeService;
import ro.dragomiralin.data.acquisition.service.SubscriptionService;
import ro.dragomiralin.data.acquisition.common.exception.HttpError;

import javax.annotation.PostConstruct;


@Slf4j
@Service
@RequiredArgsConstructor
public class SubscribeServiceImpl implements SubscribeService {
    private final SubscriptionService subscriptionService;
    private final AcquisitionService acquisitionService;
    private static final String NUMBER_SIGN = "#";
    private final MQTTClient mqttClient;

    @PostConstruct
    public void subscribeAll() {
        subscriptionService.list().forEach(subscription -> {
            try {
                this.subscribeWithResponse(subscription);
            } catch (Exception e) {
                log.error("Subscribe failed, topic: {}", subscription.getTopic(), e);
            }
        });
    }

    public void unsubscribe(String topic) {
        subscriptionService.deleteByTopic(topic);
        try {
            mqttClient.getClient().subscribeWithResponse(topic);
            log.info("Unsubscribe to: " + topic);
        } catch (Exception e) {
            log.error("An error occurred while unsubscribing to {}", topic, e);
            throw HttpError.badRequest(String.format("Unsubscribe failed, topic %s.", topic));
        }
    }

    @Override
    public void subscribe(String userId, String topic) {
        if (topic.contains(NUMBER_SIGN)) {
            throw HttpError.badRequest(String.format("It is not possible to subscribe to %s topic.", NUMBER_SIGN));
        }

        var subscription = subscriptionService.create(Subscription.builder()
                .topic(topic)
                .userId(userId)
                .build());

        this.subscribeWithResponse(subscription);
        log.info("Subscribed with success to {} topic.", topic);
    }

    private void subscribeWithResponse(Subscription subscription) {
        try {
            mqttClient.getClient().subscribeWithResponse(subscription.getTopic(), (s, mqttMessage)
                    -> acquisitionService.save(subscription.getTopic(), mqttMessage));
        } catch (Exception e) {
            log.error("An error occurred while subscribing to {}.", subscription.getTopic(), e);
            throw HttpError.badRequest(String.format("Subscribe failed, details=%s", e.getMessage()));
        }
    }

}
