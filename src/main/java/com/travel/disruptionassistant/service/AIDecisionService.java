package com.travel.disruptionassistant.service;

import com.travel.disruptionassistant.model.DisruptionContext;
import com.travel.disruptionassistant.model.Recommendation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AIDecisionService {

    private final AnthropicChatModel chatModel;
    private final ObjectMapper objectMapper;

    public AIDecisionService(AnthropicChatModel chatModel) {
        this.chatModel = chatModel;
        this.objectMapper = new ObjectMapper();
    }

    public Recommendation generateRecommendation(DisruptionContext context) {
        String prompt = buildPrompt(context);
        try {
            String response = chatModel.call(new Prompt(
                    List.of(new UserMessage(prompt))
            )).getResult().getOutput().getText();

            System.out.println("AI Response: " + response);
            return parseResponse(response);
        } catch (Exception e) {
            System.out.println("AI call failed: " + e.getMessage());
            return fallbackRecommendation();
        }
    }

    private String buildPrompt(DisruptionContext context) {
        return String.format("""
            A flight has been disrupted. Here are the details:
            
            Flight: %s
            Status: %s
            Delay: %d minutes
            
            User Itinerary: %s
            Budget: %d INR
            Avoid Night Flights: %s
            
            Please suggest the best recovery options.
            Respond ONLY in this JSON format with no extra text:
            {
                "summary": "brief summary of situation",
                "options": ["option 1", "option 2", "option 3"]
            }
            """,
                context.getEvent().getFlightNumber(),
                context.getEvent().getStatus(),
                context.getEvent().getDelayMinutes(),
                context.getItinerary().getFlights(),
                context.getPreferences().getBudget(),
                context.getPreferences().isAvoidNightFlights()
        );
    }

    private Recommendation parseResponse(String response) {
        try {
            String clean = response
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();
            return objectMapper.readValue(clean, Recommendation.class);
        } catch (Exception e) {
            System.out.println("Parse failed: " + e.getMessage());
            return fallbackRecommendation();
        }
    }

    private Recommendation fallbackRecommendation() {
        Recommendation rec = new Recommendation();
        rec.setSummary("Unable to generate AI recommendation");
        rec.setOptions(List.of("Contact airline", "Check for alternatives", "Wait for update"));
        return rec;
    }
}