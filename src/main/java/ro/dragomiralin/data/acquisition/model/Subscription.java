package ro.dragomiralin.data.acquisition.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Subscription {
    private String id;
    private String userId;
    private String topic;
    private String clientId;

}
