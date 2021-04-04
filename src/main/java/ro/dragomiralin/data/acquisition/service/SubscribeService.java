package ro.dragomiralin.data.acquisition.service;

public interface SubscribeService {
    String subscribeWithResponse(String topic);
    void unsubscribe(String topic);
    void subscribe();
}
