package com.example.bai3;

public class AppStatus {
    private static boolean isRunning = false;

    public static boolean isRunning() {
        return isRunning;
    }

    public static void setRunning(boolean running) {
        isRunning = running;
    }
}
