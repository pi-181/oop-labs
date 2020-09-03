package com.demkom58.lab13.event;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventLogger implements IProductListener {
    private final String logFileName = "Log.txt";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");

    private BufferedWriter bufferedWriter;
    private boolean enabled = true;

    public EventLogger() {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onProductEvent(ProductEvent event) {
        if (!enabled)
            return;

        final String time = sdf.format(new Date(event.getTime()));
        writeString(time + " " + event.getSource().toString());
    }

    public void writeString(String string) {
        try {
            bufferedWriter.write(string + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogFileName() {
        return logFileName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
