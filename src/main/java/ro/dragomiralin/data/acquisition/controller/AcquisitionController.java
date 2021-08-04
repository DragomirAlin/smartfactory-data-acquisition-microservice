package ro.dragomiralin.data.acquisition.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ro.dragomiralin.data.acquisition.common.Data;
import ro.dragomiralin.data.acquisition.model.Message;
import ro.dragomiralin.data.acquisition.service.AcquisitionService;
import ro.dragomiralin.data.acquisition.service.PublishService;
import ro.dragomiralin.data.acquisition.service.SubscribeService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class AcquisitionController {
    private final PublishService publishService;
    private final SubscribeService subscribeService;
    private final AcquisitionService acquisitionService;

    @GetMapping
    public String test(@AuthenticationPrincipal Jwt principal) {
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

    @GetMapping("/data")
    public List<Data> allData(@AuthenticationPrincipal Jwt principal) {
        return acquisitionService.getAll();
    }

    @GetMapping("/data/{topic}")
    public List<Data> getDataByTopic(@AuthenticationPrincipal Jwt principal, @PathVariable String topic) {
        return acquisitionService.getDataByTopic(topic);
    }
}
