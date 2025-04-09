package io.github.nachowski.providers;

import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.Request;

public class Providers {
    private final static Dotenv dotenv = Dotenv.load();

    /* docs: https://platform.openai.com/docs/quickstart?api-mode=chat&lang=curl */
    public static final Provider OPENAI = () -> new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer " + dotenv.get("OPENAI_API_KEY"));

    /* docs: https://docs.anthropic.com/en/api/openai-sdk */
    public static final Provider ANTHROPIC = () -> new Request.Builder()
            .url("https://api.anthropic.com/v1/chat/completions")
            .addHeader("x-api-key", dotenv.get("ANTHROPIC_API_KEY"))
            .addHeader("content-type", "application/json")
            .addHeader("anthropic-version", "2023-06-01");

    /* docs: https://ai.google.dev/gemini-api/docs/openai */
    public static final Provider GEMINI = () -> new Request.Builder()
            .url("https://generativelanguage.googleapis.com/v1beta/openai/chat/completions")
            .addHeader("Authorization", "Bearer " + dotenv.get("GEMINI_API_KEY"))
            .addHeader("content-type", "application/json");
}
