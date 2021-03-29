package ro.dragomiralin.data.acquisition.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Message {
    @NotNull
    private String topic;
    @NotNull
    private String message;
    private Boolean retained;
    private Integer qos;
}
