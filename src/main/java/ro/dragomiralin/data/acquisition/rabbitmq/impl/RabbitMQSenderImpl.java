package ro.dragomiralin.data.acquisition.rabbitmq.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.dragomiralin.data.acquisition.rabbitmq.RabbitMQSenderService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQSenderImpl implements RabbitMQSenderService {
    private final RabbitTemplate rabbitTemplate;
    @Value("${smartfactory.rabbitmq.exchange}")
    private String exchange;
    @Value("${smartfactory.rabbitmq.routingkey}")
    private String routingkey;

    @Override
    public void send(Object data) {
        rabbitTemplate.convertAndSend(exchange, routingkey, data);
    }
}
