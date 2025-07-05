package com.appointment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserFrame extends JFrame {
    private User user;
    private DefaultListModel<Appointment> appointmentListModel;

    public UserFrame(User user) {
        this.user = user;
        setTitle("Appointment System - User Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Appointment List
        appointmentListModel = new DefaultListModel<>();
        updateAppointmentList();
        JList<Appointment> appointmentList = new JList<>(appointmentListModel);
        JScrollPane scrollPane = new JScrollPane(appointmentList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JButton bookButton = new JButton("Book Appointment");
        bookButton.addActionListener(e -> bookAppointment());
        JButton cancelButton = new JButton("Cancel Appointment");
        cancelButton.addActionListener(e -> cancelAppointment(appointmentList.getSelectedValue()));
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        buttonPanel.add(bookButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void updateAppointmentList() {
        appointmentListModel.clear();
        List<Appointment> appointments = DatabaseConnection.getAppointments(user.getUserId());
        for (Appointment appointment : appointments) {
            appointmentListModel.addElement(appointment);
        }
    }

    private void bookAppointment() {
        new BookingFrame(user, this::updateAppointmentList).setVisible(true);
    }

    private void cancelAppointment(Appointment appointment) {
        if (appointment == null) {
            JOptionPane.showMessageDialog(this, "Select an appointment to cancel", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (DatabaseConnection.cancelAppointment(appointment.getAppointmentId())) {
            updateAppointmentList();
            JOptionPane.showMessageDialog(this, "Appointment cancelled", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to cancel appointment", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}