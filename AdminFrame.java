package com.appointment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private DefaultListModel<Appointment> appointmentListModel;
    private DefaultListModel<Resource> resourceListModel;

    public AdminFrame() {
        setTitle("Appointment System - Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Content Panel (Resources and Appointments)
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        // Resources Panel
        JPanel resourcePanel = new JPanel(new BorderLayout(5, 5));
        resourcePanel.setBorder(BorderFactory.createTitledBorder("Resources"));
        resourceListModel = new DefaultListModel<>();
        updateResourceList();
        JList<Resource> resourceList = new JList<>(resourceListModel);
        JScrollPane resourceScroll = new JScrollPane(resourceList);
        resourcePanel.add(resourceScroll, BorderLayout.CENTER);

        JPanel resourceButtonPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        JButton addResourceButton = new JButton("Add Resource");
        addResourceButton.addActionListener(e -> addResource());
        resourceButtonPanel.add(addResourceButton);
        resourcePanel.add(resourceButtonPanel, BorderLayout.SOUTH);
        contentPanel.add(resourcePanel);

        // Appointments Panel
        JPanel appointmentPanel = new JPanel(new BorderLayout(5, 5));
        appointmentPanel.setBorder(BorderFactory.createTitledBorder("All Appointments"));
        appointmentListModel = new DefaultListModel<>();
        updateAppointmentList();
        JList<Appointment> appointmentList = new JList<>(appointmentListModel);
        JScrollPane appointmentScroll = new JScrollPane(appointmentList);
        appointmentPanel.add(appointmentScroll, BorderLayout.CENTER);
        contentPanel.add(appointmentPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> logout());
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateResourceList() {
        resourceListModel.clear();
        List<Resource> resources = DatabaseConnection.getResources();
        for (Resource resource : resources) {
            resourceListModel.addElement(resource);
        }
    }

    private void updateAppointmentList() {
        appointmentListModel.clear();
        List<Appointment> appointments = DatabaseConnection.getAllAppointments();
        for (Appointment appointment : appointments) {
            appointmentListModel.addElement(appointment);
        }
    }

    private void addResource() {
        JTextField nameField = new JTextField();
        JTextField descField = new JTextField();
        Object[] message = {
                "Resource Name:", nameField,
                "Description:", descField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "Add Resource", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String description = descField.getText();
            if (DatabaseConnection.addResource(name, description)) {
                updateResourceList();
                JOptionPane.showMessageDialog(this, "Resource added", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add resource", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}