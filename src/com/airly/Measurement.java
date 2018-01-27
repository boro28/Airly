package com.airly;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Measurement {
    private String airQualityIndex = null;
    private String pm1 = null;
    private String pm25 = null;
    private String pm10 = null;
    private String pressure = null;
    private String humidity = null;
    private String temperature = null;


    public void setAirQualityIndex(double value) {
        this.airQualityIndex = String.format("%.0f", value) + " CAQI";
    }

    public void setPm1(double value) {
        this.pm1 = String.format("%.0f", value) + " μg/m3";
    }

    public void setPm25(double value) {
        this.pm25 = String.format("%.0f", value) + " μg/m3";
    }

    public void setPm10(double value) {
        this.pm10 = String.format("%.0f", value) + " μg/m3";
    }

    public void setPressure(double value) {
        this.pressure = String.format("%.0f", value / 10) + " hPa";
    }

    public void setHumidity(double value) {
        this.humidity = String.format("%.0f", value) + " %";
    }

    public void setTemperature(double value) {
        this.temperature = String.format("%.0f", value) + " °C";
    }

    public String[] getMeasurment() {
        String[] result = {this.airQualityIndex, this.pm1, this.pm25, this.pm10, this.pressure, this.humidity, this.temperature
        };
        return result;
    }

    public static List<Measurement> setMeasurments(JsonParser parser, boolean withHistory) throws IOException {
        List<Measurement> measurements = new ArrayList<Measurement>();
        Measurement measurement = new Measurement();
        while (!parser.isClosed() && (withHistory || measurements.isEmpty())) {

            JsonToken jsonToken = parser.nextToken();
            if (JsonToken.FIELD_NAME.equals(jsonToken)) {

                String fieldName = parser.getCurrentName();

                jsonToken = parser.nextToken();

                switch (fieldName) {
                    case "airQualityIndex":
                        measurement = new Measurement();
                        measurement.setAirQualityIndex(parser.getValueAsDouble());
                        break;
                    case "pm1":
                        measurement.setPm1(parser.getValueAsDouble());
                        break;
                    case "pm25":
                        measurement.setPm25(parser.getValueAsDouble());
                        break;
                    case "pm10":
                        measurement.setPm10(parser.getValueAsDouble());
                        break;
                    case "pressure":
                        measurement.setPressure(parser.getValueAsDouble());
                        break;
                    case "humidity":
                        measurement.setHumidity(parser.getValueAsDouble());
                        break;
                    case "temperature":
                        measurement.setTemperature(parser.getValueAsDouble());
                        measurements.add(measurement);
                        break;
                    default:
                }
            }
        }
        return measurements;
    }


}
