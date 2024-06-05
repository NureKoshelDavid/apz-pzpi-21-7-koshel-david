package com.iot.SmartDeviceMS;

public class SensorData {
    private String temperature;
    private String humidity;
    private String ldrValue;

    public SensorData(String temperature, String humidity, String ldrValue) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.ldrValue = ldrValue;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLdrValue() {
        return ldrValue;
    }

    public void setLdrValue(String ldrValue) {
        this.ldrValue = ldrValue;
    }


}
