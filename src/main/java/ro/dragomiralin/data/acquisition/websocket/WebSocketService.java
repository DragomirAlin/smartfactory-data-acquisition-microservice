package ro.dragomiralin.data.acquisition.websocket;

import ro.dragomiralin.data.acquisition.common.Data;

public interface WebSocketService {
    void notify(Data data);
}
