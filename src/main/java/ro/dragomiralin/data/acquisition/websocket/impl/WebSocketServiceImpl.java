package ro.dragomiralin.data.acquisition.websocket.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.common.Data;
import ro.dragomiralin.data.acquisition.websocket.WebSocketService;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {
    private final SimpMessagingTemplate template;

    @Override
    public void notify(Data data) {
        this.template.convertAndSend("/message",  data);
    }
}
