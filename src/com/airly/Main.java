package com.airly;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CmdParser cmdParser = new CmdParser();
        try {
            CommandLine cmd = cmdParser.parseCmd(args);

            String apikey;
            if (System.getenv().containsKey("API_KEY")) {
                apikey = System.getenv("API_KEY");
            } else
                apikey = cmd.getOptionValue("api_key");

            String latitude = cmd.getOptionValue("latitude");
            String longitude = cmd.getOptionValue("longitude");
            String sensorID = cmd.getOptionValue("sensor_id");
            String url;

            if (sensorID != null) {
                url = "https://airapi.airly.eu/v1/sensor/measurements?sensorId=1" + sensorID;
            } else {
                url = "https://airapi.airly.eu/v1/nearestSensor/measurements?" + "latitude=" + latitude + "&longitude=" + longitude + "&maxdistance=1000";
            }
            JsonParser parse = JsonOperator.getJsonParser(url, apikey);
            List<Measurement> measurements = new ArrayList<Measurement>();
            if (cmd.hasOption("history")) {
                measurements = Measurement.setMeasurments(parse, true);
            } else {
                measurements = Measurement.setMeasurments(parse, false);
            }
            String[][] data = new String[measurements.size()][7];
            for (int i = 0; i < measurements.size(); i++) {
                data[i] = measurements.get(i).getMeasurment();
            }

            if (data.length == 0) {
                throw new IllegalArgumentException("Wrong sensor ID or localization");
            }

            View.viewMeasurements(data);

        } catch (ParseException ex) {
            System.out.println("Argument parsing failed" + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Try again. " + ex.getMessage());
        }catch(IOException ex){
            System.out.println("Connection failed");
        }
    }
}

