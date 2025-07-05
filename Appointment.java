package com.appointment;

import java.sql.Timestamp;

public class Appointment {
    private int appointmentId;
    private int userId;
    private int resourceId;
    private String resourceName;
    private Timestamp timeslot;

    public Appointment(int appointmentId, int userId, int resourceId, String resourceName, Timestamp timeslot) {
        this.appointmentId = appointmentId;
        this.userId = userId;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.timeslot = timeslot;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public Timestamp getTimeslot() {
        return timeslot;
    }

    @Override
    public String toString() {
        return "Appointment: " + resourceName + " at " + timeslot;
    }
}