package com.travel.disruptionassistant.service;

import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    public void logEvent(String flightNumber, int delayMinutes) {
        System.out.println("═══════════════════════════════════");
        System.out.println("📥 EVENT RECEIVED");
        System.out.println("   Flight  : " + flightNumber);
        System.out.println("   Delay   : " + delayMinutes + " mins");
        System.out.println("═══════════════════════════════════");
    }

    public void logDisruption(String flightNumber) {
        System.out.println("⚠️  DISRUPTION DETECTED");
        System.out.println("   Flight  : " + flightNumber);
        System.out.println("   Status  : Threshold exceeded");
    }

    public void logAIGenerated() {
        System.out.println("🤖 AI RECOMMENDATION GENERATED");
    }

    public void logNotificationSent(String userId) {
        System.out.println("🔔 NOTIFICATION SENT");
        System.out.println("   User    : " + userId);
        System.out.println("═══════════════════════════════════");
    }

    public void logNoDisruption(String flightNumber) {
        System.out.println("✅ NO DISRUPTION");
        System.out.println("   Flight  : " + flightNumber);
        System.out.println("═══════════════════════════════════");
    }
}