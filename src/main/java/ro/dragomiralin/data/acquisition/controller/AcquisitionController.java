package ro.dragomiralin.data.acquisition.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ro.dragomiralin.data.acquisition.model.Message;
import ro.dragomiralin.data.acquisition.service.PublishService;
import ro.dragomiralin.data.acquisition.service.SenderService;
import ro.dragomiralin.data.acquisition.service.SubscribeService;
import ro.dragomiralin.data.acquisition.service.rabbitmq.model.SubscriptionDTO;

@Slf4j
@RestController
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class AcquisitionController {
    private final PublishService publishService;
    private final SubscribeService subscribeService;
    private final SenderService senderService;

    @GetMapping
    public String test(@AuthenticationPrincipal Jwt principal) {
        senderService.send(SubscriptionDTO.builder().id("1").build());
        return String.format("Endpoint Test from mqtt-microservice. User=%s", principal.getClaimAsString("preferred_username"));
    }

    @PostMapping("/publish")
    public void publish(@AuthenticationPrincipal Jwt principal, @RequestBody Message message) {
        publishService.publish(message);
    }

    @DeleteMapping("/unsubscribe")
    public void unsubscribe(@AuthenticationPrincipal Jwt principal, @RequestParam String topic) {
        subscribeService.unsubscribe(topic);
    }

    @PostMapping("/subscribe")
    public void subscribe(@AuthenticationPrincipal Jwt principal, @RequestParam String topic) {
        String userId = principal.getClaimAsString("sub");
        subscribeService.subscribe(userId, topic);
    }
}
