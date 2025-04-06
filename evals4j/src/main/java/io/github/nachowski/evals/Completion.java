package io.github.nachowski.evals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import io.github.nachowski.providers.Model;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Completion {
    final Model model;
    final OkHttpClient client;
    final Gson gson = new Gson();

    public Completion(Model model) {
        this.model = model;
        client = new OkHttpClient();
    }

    public String getCompletion(String systemPrompt, String userPrompt) {
        return getCompletion(model.provider.getUrl(), model.provider.getApiKey(), model.modelName, systemPrompt,
                userPrompt);
    }

    // Request classes
    class Message {
        String role;
        String content;

        Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    class CompletionRequest {
        String model;
        List<Message> messages;

        CompletionRequest(String model, List<Message> messages) {
            this.model = model;
            this.messages = messages;
        }
    }

    // Response classes
    class CompletionChoice {
        Message message;
    }

    class CompletionResponse {
        List<CompletionChoice> choices;
    }

    private String getCompletion(String url, String apiKey, String modelName,
            String systemPrompt, String userPrompt) {

        // Build messages list
        List<Message> messages = new ArrayList<>();

        if (systemPrompt != null && !systemPrompt.isEmpty()) {
            messages.add(new Message("system", systemPrompt));
        }

        if (userPrompt != null && !userPrompt.isEmpty()) {
            messages.add(new Message("user", userPrompt));
        }

        // Create request object
        CompletionRequest completionRequest = new CompletionRequest(
                modelName,
                messages);

        // Serialize to JSON
        String jsonBody = gson.toJson(completionRequest);
        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json; charset=utf-8"));

        // Build the request
        Request request = new Request.Builder()
                .url(buildFullUrl(url))
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        try {
            // Execute the request
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // Deserialize the response
            String responseBody = response.body().string();
            CompletionResponse completionResponse = gson.fromJson(responseBody, CompletionResponse.class);

            if (completionResponse.choices != null && !completionResponse.choices.isEmpty()) {
                return completionResponse.choices.get(0).message.content.trim();
            }

            throw new RuntimeException("No completion found in response");

        } catch (IOException e) {
            throw new RuntimeException("Failed to get completion: " + e.getMessage(), e);
        }
    }

    private HttpUrl buildFullUrl(String baseUrl) {
        HttpUrl httpUrl = HttpUrl.parse(baseUrl);
        if (httpUrl == null) {
            throw new IllegalArgumentException("Invalid base URL: " + baseUrl);
        }
        return httpUrl.newBuilder()
                .addPathSegment("v1")
                .addPathSegment("chat")
                .addPathSegment("completions")
                .build();
    }
}
