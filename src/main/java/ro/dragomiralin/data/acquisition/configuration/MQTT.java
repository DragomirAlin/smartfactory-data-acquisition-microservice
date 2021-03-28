package ro.dragomiralin.data.acquisition.configuration;


import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MQTT {
    @Value("${mqtt.publisher-id}")
    private static String MQTT_PUBLISHER_ID;
    @Value("${mqtt.server}")
    private static String MQTT_SERVER_ADDRESS;
    private static IMqttClient instance;

    public static IMqttClient getClient() {
        try {
            if (Objects.isNull(instance)) {
                instance = new MqttClient(MQTT_SERVER_ADDRESS, MQTT_PUBLISHER_ID);
            }

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);

            if (!instance.isConnected()) {
                instance.connect(options);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

        return instance;
    }

    private MQTT() {
    }

}
