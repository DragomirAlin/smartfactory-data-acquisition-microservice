package ro.dragomiralin.data.acquisition.common;

import lombok.Builder;
import lombok.Data;
import ro.dragomiralin.data.acquisition.common.DeviceType;

import java.util.Date;

@Data
@Builder
public  class Metadata {
    private String macAddress;
    private DeviceType device;
    private String location;
    private Date timestamp;
    private String maintainer;
    private String lastMaintain;
}