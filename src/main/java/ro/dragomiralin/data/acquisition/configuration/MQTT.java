package ro.dragomiralin.data.acquisition.configuration;


import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class MQTT {
    @Value("${mqtt.publisher-id}")
    private static String MQTT_PUBLISHER_ID;
    @Value("${mqtt.server}")
    private static String MQTT_SERVER_ADDRESS;
    private static IMqttClient instance;

    private MQTT() {
    }

    public static IMqttClient getClient() {
        try {
            if (Objects.isNull(instance)) {
                instance = new MqttClient(MQTT_SERVER_ADDRESS, MQTT_PUBLISHER_ID);
            }

            if (!instance.isConnected()) {
                instance.connect(getOption());
            }
        } catch (MqttException e) {
            log.error("An error occurred while return MQTT client.", e);
        }
        return instance;
    }

    private static MqttConnectOptions getOption() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
        return options;
    }
}
