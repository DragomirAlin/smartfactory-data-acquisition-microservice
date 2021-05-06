package ro.dragomiralin.data.acquisition.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Payload {
    private String macAddress;
    private Object payload;
    private Object metadata;

    @Data
    @Builder
    public static class Metadata {
        private DeviceType device;
        private String location;
        private Date timestamp;
        private String maintainer;
        private String lastMaintain;
    }
}
