package com.example.id.moevents;

import net.fortuna.ical4j.model.Component;

/**
 * Created by greg on 4/9/15.
 */
public class CalendarEvent {
    private int id;
    private String location;
    private String summary;
    private String description;
    private String start;
    private String end;
    private String url;
    private Boolean favorited = false;
    private double distance = 0.0;

    private final String KEY_LOCATION = "LOCATION";
    private final String KEY_SUMMARY = "SUMMARY";
    private final String KEY_DESCRIPTION = "DESCRIPTION";
    private final String KEY_START = "DTSTART";
    private final String KEY_END = "DTEND";
    private final String KEY_URL = "URL";


    public CalendarEvent(int id, Component component) {
        //Log.i("calendarEvent", component.toString());
        this.id = id;
        location = component.getProperty(KEY_LOCATION).toString().split(":")[1];
        summary = component.getProperty(KEY_SUMMARY).toString().split(":")[1];
        description = component.getProperty(KEY_DESCRIPTION).toString().split(":")[1];
        start = component.getProperty(KEY_START).toString().split(":")[1];
        end = component.getProperty(KEY_END).toString().split(":")[1];
        url = component.getProperty(KEY_URL).toString().split(":")[1];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean isFavorited() { return favorited; }

    public void setFavorite(Boolean val) { favorited = val; }

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
                "id='" + id + '\'' +
                ", location='" + location + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
