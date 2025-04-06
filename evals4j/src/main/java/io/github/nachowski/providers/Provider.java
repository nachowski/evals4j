package io.github.nachowski.providers;

/**
 * Interface representing a provider for an LLM (Large Language Model).
 * This interface defines the methods for an OpenAI-compatible API provider.
 */
public interface Provider {
    String getUrl();
    String getApiKey();
}
