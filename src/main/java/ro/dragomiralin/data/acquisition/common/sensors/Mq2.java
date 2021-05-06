package ro.dragomiralin.data.acquisition.common.sensors;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Mq2 {
    private double gasValue;
}
