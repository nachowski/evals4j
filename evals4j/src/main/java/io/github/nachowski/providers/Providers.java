package io.github.nachowski.providers;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;

public class Providers {
    private final static Dotenv dotenv = Dotenv.load();

    public static final Provider OPENAI = () -> new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer " + dotenv.get("OPENAI_API_KEY"));

    public static final Provider ANTHROPIC = () -> new Request.Builder()
            .url("https://api.anthropic.com/v1/messages")
            .addHeader("x-api-key", dotenv.get("ANTHROPIC_API_KEY"))
            .addHeader("content-type", "application/json")
            .addHeader("anthropic-version", "2023-06-01");
}
