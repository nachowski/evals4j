package io.github.nachowski.evals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import io.github.nachowski.providers.Model;
import io.github.nachowski.util.EvaluationException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Completion {
    final Model model;
    final OkHttpClient client;
    final Gson gson = new Gson();

    public Completion(Model model) {
        this.model = model;
        client = new OkHttpClient();
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
        long max_tokens;
        List<Message> messages;

        CompletionRequest(String model, List<Message> messages, long max_tokens) {
            this.model = model;
            this.messages = messages;
            this.max_tokens = max_tokens;
        }
    }

    // Response classes
    class CompletionChoice {
        Message message;
    }

    class CompletionResponse {
        List<CompletionChoice> choices;
    }


    public enum MAX_TOKENS {
        SHORT(128),
        DEFAULT(1024),
        LONG(4096);

        private final int value;

        MAX_TOKENS(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public String getCompletion(String systemPrompt, String userPrompt) {
        return getCompletion(systemPrompt, userPrompt, MAX_TOKENS.DEFAULT);
    }

    public String getCompletion(String systemPrompt, String userPrompt, MAX_TOKENS maxTokens) {

        // Build messages list
        List<Message> messages = new ArrayList<>();

        if (systemPrompt != null && !systemPrompt.isEmpty()) {
            messages.add(new Message("system", systemPrompt));
        }

        messages.add(new Message("user", userPrompt));

        // Create request object
        CompletionRequest completionRequest = new CompletionRequest(
                model.modelName,
                messages,
                maxTokens.getValue());

        // Serialize to JSON
        String jsonBody = gson.toJson(completionRequest);
        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json; charset=utf-8"));

        // Build the request
        Request request = model.provider.getRequest().post(body).build();

        // TODO DEBUGGING!
        // logRequestDetails(request);

        try {
            // Execute the request
            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new EvaluationException("Unexpected response " + response);
            }

            // Deserialize the response
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new EvaluationException("Response body is null");
            }

            CompletionResponse completionResponse = gson.fromJson(responseBody.string(), CompletionResponse.class);

            if (completionResponse.choices != null && !completionResponse.choices.isEmpty()) {
                return completionResponse.choices.get(0).message.content.trim();
            }

            throw new EvaluationException("No completion found in response");

        } catch (IOException e) {
            throw new EvaluationException("Failed to get completion: " + e.getMessage(), e);
        }
    }
}
