package ro.dragomiralin.data.acquisition.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ro.dragomiralin.data.acquisition.common.Data;
import ro.dragomiralin.data.acquisition.model.Message;
import ro.dragomiralin.data.acquisition.model.Pagination;
import ro.dragomiralin.data.acquisition.service.AcquisitionService;
import ro.dragomiralin.data.acquisition.service.PublishService;
import ro.dragomiralin.data.acquisition.service.SubscribeService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/mqtt")
@RequiredArgsConstructor
public class AcquisitionController {
    private final PublishService publishService;
    private final SubscribeService subscribeService;
    private final AcquisitionService acquisitionService;

    @GetMapping
    public ResponseEntity<String> test(@AuthenticationPrincipal Jwt principal) {
        return new ResponseEntity<>(String.format("Endpoint Test from mqtt-microservice. User=%s", principal.getClaimAsString("preferred_username")),
                HttpStatus.ACCEPTED);
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publish(@AuthenticationPrincipal Jwt principal, @RequestBody Message message) {
        publishService.publish(message);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@AuthenticationPrincipal Jwt principal, @RequestParam String topic) {
        subscribeService.unsubscribe(topic);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@AuthenticationPrincipal Jwt principal, @RequestParam String topic) {
        String userId = principal.getClaimAsString("sub");
        subscribeService.subscribe(userId, topic);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/data")
    public ResponseEntity<Map<String, Object>> allData(@AuthenticationPrincipal Jwt principal, @RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(acquisitionService.getAllData(Pagination.builder()
                .page(page)
                .size(size)
                .build()), HttpStatus.OK);
    }

    @GetMapping("/data/{topic}")
    public ResponseEntity<Map<String, Object>> getDataByTopic(@AuthenticationPrincipal Jwt principal, @PathVariable String topic, @RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(acquisitionService.getDataByTopic(topic,
                Pagination.builder()
                        .page(page)
                        .size(size)
                        .build()), HttpStatus.OK);
    }

    @PostMapping("/data/search")
    public ResponseEntity<List<Data>> searchData(@RequestBody Map<String, Object> criteria) {
        // return new ResponseEntity<>(acquisitionService.searchData(textSearch), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
