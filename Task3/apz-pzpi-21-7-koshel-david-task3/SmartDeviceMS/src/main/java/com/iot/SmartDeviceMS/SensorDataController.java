package com.iot.SmartDeviceMS;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class SensorDataController {
    @PostMapping("/data")
    public void receiveData(@RequestBody SensorData sensorData) {
        System.out.println("Temperature: " + sensorData.getTemperature());
        System.out.println("Humidity: " + sensorData.getHumidity());
        System.out.println("LDR Value: " + sensorData.getLdrValue());
    }

    @GetMapping("/data")
    public SensorData receiveData() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String temp = restTemplate.getForObject("https://api.thingspeak.com/channels/2559929/fields/1.json?api_key=B55GMLS9Y1O2C9X4&results=1",String.class);
        String hum = restTemplate.getForObject("https://api.thingspeak.com/channels/2559929/fields/2.json?api_key=B55GMLS9Y1O2C9X4&results=1",String.class);
        String ldr = restTemplate.getForObject("https://api.thingspeak.com/channels/2559929/fields/3.json?api_key=B55GMLS9Y1O2C9X4&results=1",String.class);
        System.out.println("OKEY GET IS WORKING");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Feed> feedsT = objectMapper.readValue(temp, ThingSpeakResponse.class).getFeeds();
        List<Feed> feedsH = objectMapper.readValue(hum, ThingSpeakResponse.class).getFeeds();
        List<Feed> feedsL = objectMapper.readValue(ldr, ThingSpeakResponse.class).getFeeds();
        System.out.println("Temperature: " + feedsT.get(0).getField1());
        System.out.println("Humidity: " + feedsH.get(0).getField2());
        System.out.println("LDR Value: " + feedsL.get(0).getField3());

        return new SensorData(feedsT.get(0).getField1(),feedsH.get(0).getField2(),feedsL.get(0).getField3());
    }
}
