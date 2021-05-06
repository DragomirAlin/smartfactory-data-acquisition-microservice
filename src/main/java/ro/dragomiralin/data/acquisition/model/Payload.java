package ro.dragomiralin.data.acquisition.model;

import lombok.Builder;
import lombok.Data;
import ro.dragomiralin.data.acquisition.common.Metadata;

@Data
@Builder
public class Payload {
    private Object payload;
    private Metadata metadata;
}
