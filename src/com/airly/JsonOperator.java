package com.airly;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;


public class JsonOperator {

    public static JsonParser getJsonParser(String url, String apikey) throws IOException, IllegalArgumentException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("apikey", apikey);
        connection.setRequestProperty("Accept", "application/json");
        if (connection.getResponseCode() != 200) {
            switch (connection.getResponseCode()) {
                case 400:
                    throw new IllegalArgumentException("Input validation error occurred");
                case 401:
                    throw new IllegalArgumentException("Unauthorized Access. Wrong Api_Key.");
                case 403:
                    throw new IllegalArgumentException("Forbidden Access. Wrong Api_Key.");
                case 404:
                    throw new IllegalArgumentException("Element Not Found");
                case 500:
                    throw new IllegalArgumentException("Unexpected error occurred");
                default:
                    throw new IllegalArgumentException("Unknown Error. Try again");
            }
        }
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(new InputStreamReader((InputStream) connection.getContent()));
        return parser;
    }
}