package ro.dragomiralin.data.acquisition.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Payload {
    private Object payload;
    private Metadata metadata;
}
