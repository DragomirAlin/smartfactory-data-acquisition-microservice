package ro.dragomiralin.data.acquisition.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mqtt")
public class AcquisitionController {

    @GetMapping
    public String home() {
        return "Response from /mqtt endpoint.";
    }
}
