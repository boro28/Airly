package com.airly;

import org.apache.commons.cli.*;

public class CmdParser {

    public Options options;

    public CmdParser() {
        Options options = new Options();
        options.addOption("lat", "latitude", true, "Geographic coordinate that specifies the north–south position of a point on the Earth's surface.It ranges from 0° at the Equator to 90° (North or South) at the poles.");
        options.addOption("long", "longitude", true, "Geographic coordinate that specifies the east-west position of a point on the Earth's surface.The longitude of other places is measured as the angle east or west from the Prime Meridian, ranging from 0° at the Prime Meridian to +180° eastward and −180° westward. ");
        options.addOption("id", "sensor_id", true, "Sensor ID");
        options.addOption("api_key", null, true, "Api Key for Airly");
        options.addOption("history", null, false, "Shows history for given sensor from last 24 hours");
        options.addOption("h", "help", false, "Show help");
        this.options = options;
    }


    public CommandLine parseCmd(String[] args) throws ParseException, IllegalArgumentException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(this.options, args, false);

        String header = "You can either chose information from specified sensor" +
                "or from location specified by latitude and longitude" +
                "List of available options(history and api_key are optional):\n\n";
        String footer = "Program made by Szymon Borkowski";
        HelpFormatter formatter = new HelpFormatter();


        if (cmd.hasOption("help")) {
            formatter.printHelp("Airly", header, this.options, footer, true);
        }
        if (cmd.getOptions().length == 0) {
            formatter.printHelp("Airly", header, this.options, footer, true);
            throw new IllegalArgumentException("Illegal number of arguments.");
        }

        if (!(cmd.hasOption("sensor_id") || (cmd.hasOption("latitude") && cmd.hasOption("longitude")))) {
            formatter.printHelp("Airly", header, this.options, footer, true);
            throw new IllegalArgumentException("Sensor coordinates or its ID is needed");
        }

        return cmd;
    }
}
