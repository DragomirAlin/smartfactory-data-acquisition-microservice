package ro.dragomiralin.data.acquisition.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.model.Subscription;
import ro.dragomiralin.data.acquisition.repository.SubscriptionRepository;
import ro.dragomiralin.data.acquisition.service.SubscriptionService;
import ro.dragomiralin.data.acquisition.common.exception.HttpError;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription create(Subscription subscription) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findByTopic(subscription.getTopic());

        if (optionalSubscription.isPresent()) {
            throw HttpError.badRequest(String.format("Topic = %s already register in broker.", subscription.getTopic()));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public void delete(String id) {
        var subscription = this.getById(id);
        subscriptionRepository.delete(subscription);
    }

    @Override
    public List<Subscription> list() {
        return subscriptionRepository.findAll();
    }

    @Override
    public Subscription getById(String id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> HttpError.notFound(String.format("Subscription with id = %s was not found.", id)));
    }

    @Override
    public void deleteByTopic(String topic) {
        subscriptionRepository.findByTopic(topic)
                .ifPresent(subscriptionRepository::delete);
    }
}
