package ro.dragomiralin.data.acquisition.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
    private Object payload;
    private Metadata metadata;
}
