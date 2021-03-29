package ro.dragomiralin.data.acquisition.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ro.dragomiralin.data.acquisition.model.Message;
import ro.dragomiralin.data.acquisition.service.PublishService;
import ro.dragomiralin.data.acquisition.service.SubscribeService;

@Slf4j
@RestController
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class AcquisitionController {
    private final PublishService publishService;
    private final SubscribeService subscribeService;

    @GetMapping
    public String test(@AuthenticationPrincipal Jwt principal) {
        return String.format("Endpoint Test from mqtt-microservice. User=%s", principal.getClaimAsString("preferred_username"));
    }

    @PostMapping("/publish")
    public void publish(@AuthenticationPrincipal Jwt principal, @RequestBody Message message) {
        publishService.publish(message);
    }

    @DeleteMapping("/unsubscrine/{topic}")
    public void unsubscribe(@AuthenticationPrincipal Jwt principal, @PathVariable String topic) {
        subscribeService.unsubscribe(topic);
    }

    @PostMapping("/subscribe/{topic}")
    public String subscribe(@AuthenticationPrincipal Jwt principal, @PathVariable String topic) {
        return subscribeService.subscribeWithResponse(topic);
    }
}
