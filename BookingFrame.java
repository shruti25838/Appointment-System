package com.appointment;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class BookingFrame extends JFrame {
    private User user;
    private Runnable updateCallback;
    private JComboBox<Resource> resourceCombo;
    private JTextField dateField;

    public BookingFrame(User user, Runnable updateCallback) {
        this.user = user;
        this.updateCallback = updateCallback;
        setTitle("Book Appointment");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Resource:"));
        List<Resource> resources = DatabaseConnection.getResources();
        resourceCombo = new JComboBox<>(resources.toArray(new Resource[0]));
        panel.add(resourceCombo);

        panel.add(new JLabel("Date/Time (yyyy-MM-dd HH:mm):"));
        dateField = new JTextField();
        // Pre-fill with current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        dateField.setText(now.format(formatter));
        panel.add(dateField);

        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(e -> bookAppointment());
        panel.add(bookButton);

        add(panel);
    }

    private void bookAppointment() {
        try {
            String dateStr = dateField.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = sdf.parse(dateStr);
            Timestamp timeslot = new Timestamp(date.getTime());
            Resource resource = (Resource) resourceCombo.getSelectedItem();
            if (DatabaseConnection.bookAppointment(user.getUserId(), resource.getResourceId(), timeslot)) {
                updateCallback.run();
                JOptionPane.showMessageDialog(this, "Appointment booked", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Slot already booked or invalid", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}