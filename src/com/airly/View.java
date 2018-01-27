package com.airly;


import com.bethecoder.ascii_table.ASCIITable;

public class View {
    private static String[] header = {"AirQualityIndex", "PM 1", "PM 2.5", "PM 10", "Pressure", "Humidity", "Temperature"};

    public static void viewMeasurements(String[][] data) {
        ASCIITable.getInstance().printTable(header, data);
    }

}
