package ro.dragomiralin.data.acquisition.service;

public interface SubscribeService {
    void unsubscribe(String topic);
    void subscribe(String userId, String topic);
}
