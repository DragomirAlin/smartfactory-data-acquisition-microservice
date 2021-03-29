package ro.dragomiralin.data.acquisition.service;

import ro.dragomiralin.data.acquisition.model.Message;

public interface PublishService {
    void publish(Message message);
}

