package io.github.nachowski.providers;

public class Providers {
    public static final Provider OPENAI = new Provider() {
        @Override
        public String getUrl() {
            return "https://api.openai.com";
        }

        @Override
        public String getApiKey() {
            return System.getenv("OPENAI_API_KEY");
        }
    };

    public static final Provider ANTHROPIC = new Provider() {
        
        @Override
        public String getUrl() {
            return "https://api.anthropic.com";
        }

        @Override
        public String getApiKey() {
            return System.getenv("ANTHROPIC_API_KEY");
        }
    };
}
