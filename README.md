# Appointment and Resource Scheduling System

This is a desktop-based Java application for booking and managing appointments with shared resources (e.g., doctors, meeting rooms). It supports multi-user roles (Admin and User), prevents booking conflicts, and integrates with a MySQL database for persistent storage.

## Project Overview

The system allows users to:
- Register and log in based on their role
- Book or cancel appointments
- View existing bookings

Admins have additional privileges to:
- View all users and appointments
- Add or delete resources

This project demonstrates core Java concepts including:
- Swing-based GUI
- JDBC-based database interaction
- Object-oriented design and modular architecture

## Features

- Login and registration with role selection (Admin/User)
- Appointment booking with date, time, and resource selection
- Conflict detection for overlapping appointments
- Admin panel for managing users and resources
- Clean separation between UI and logic

## Technologies Used

- Java
- Java Swing (GUI)
- MySQL (Database)
- JDBC (Database Connectivity)
