package com.travel.disruptionassistant.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class NotificationService {

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendSlackNotification(Map<String, Object> recommendation) {
        String flightNumber = (String) recommendation.get("flightNumber");
        int delayMinutes = (int) recommendation.get("delayMinutes");
        String aiSummary = (String) recommendation.get("aiSummary");

        // Build ranked flights text
        StringBuilder flightsText = new StringBuilder();
        var rankedFlights = (java.util.List<Map<String, Object>>)
                recommendation.get("rankedAlternateFlights");
        for (Map<String, Object> flight : rankedFlights) {
            flightsText.append(String.format("• %s | Dep: %s | Arr: %s | ₹%s\n",
                    flight.get("flightNumber"),
                    flight.get("departure"),
                    flight.get("arrival"),
                    flight.get("cost")));
        }

        // Build Slack message
        String message = String.format("""
            {
                "text": "✈️ *Flight Disruption Alert*",
                "blocks": [
                    {
                        "type": "header",
                        "text": {
                            "type": "plain_text",
                            "text": "✈️ Flight Disruption Alert"
                        }
                    },
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": "*Flight:* %s | *Delay:* %d mins"
                        }
                    },
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": "*AI Summary:*\\n%s"
                        }
                    },
                    {
                        "type": "section",
                        "text": {
                            "type": "mrkdwn",
                            "text": "*Ranked Alternate Flights:*\\n%s"
                        }
                    }
                ]
            }
            """, flightNumber, delayMinutes, aiSummary, flightsText.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(message, headers);

        try {
            restTemplate.postForEntity(slackWebhookUrl, request, String.class);
            System.out.println("✅ Slack notification sent for flight: " + flightNumber);
        } catch (Exception e) {
            System.out.println("❌ Slack notification failed: " + e.getMessage());
        }
    }
}