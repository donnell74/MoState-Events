package com.example.id.moevents;

import android.util.Log;

import net.fortuna.ical4j.model.Component;

/**
 * Created by greg on 4/9/15.
 */
public class CalendarEvent {
    private String location;
    private String summary;
    private String description;
    private String start;
    private String end;
    private String url;

    private final String KEY_LOCATION = "LOCATION";
    private final String KEY_SUMMARY = "SUMMARY";
    private final String KEY_DESCRIPTION = "DESCRIPTION";
    private final String KEY_START = "DTSTART";
    private final String KEY_END = "DTEND";
    private final String KEY_URL = "URL";

    public CalendarEvent(Component component) {
        //Log.i("calendarEvent", component.toString());
        location = component.getProperty(KEY_LOCATION).toString().split(":")[1];
        summary = component.getProperty(KEY_SUMMARY).toString().split(":")[1];
        description = component.getProperty(KEY_DESCRIPTION).toString().split(":")[1];
        start = component.getProperty(KEY_START).toString().split(":")[1];
        end = component.getProperty(KEY_END).toString().split(":")[1];
        url = component.getProperty(KEY_URL).toString().split(":")[1];
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "location='" + location + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", url='" + url + '\'' +
                ", KEY_LOCATION='" + KEY_LOCATION + '\'' +
                ", KEY_SUMMARY='" + KEY_SUMMARY + '\'' +
                ", KEY_DESCRIPTION='" + KEY_DESCRIPTION + '\'' +
                ", KEY_START='" + KEY_START + '\'' +
                ", KEY_END='" + KEY_END + '\'' +
                ", KEY_URL='" + KEY_URL + '\'' +
                '}';
    }
}
