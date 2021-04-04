package ro.dragomiralin.data.acquisition.service;

import ro.dragomiralin.data.acquisition.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription create(Subscription subscription);

    void delete(String id);

    List<Subscription> list();

    Subscription getById(String id);

    void deleteByTopic(String topic);
}
