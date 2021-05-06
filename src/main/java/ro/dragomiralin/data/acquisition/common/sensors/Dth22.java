package ro.dragomiralin.data.acquisition.common.sensors;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class Dth22 {
    private double temperature;
    private double humidify;
}
