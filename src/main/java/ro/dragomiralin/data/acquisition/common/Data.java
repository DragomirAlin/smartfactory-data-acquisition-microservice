package ro.dragomiralin.data.acquisition.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.Map;

@lombok.Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Data {
    private String id;
    private String topic;
    private Map<String, Object> payload;
    @CreatedDate
    private Date arriveAt;
    private Metadata metadata;
}
