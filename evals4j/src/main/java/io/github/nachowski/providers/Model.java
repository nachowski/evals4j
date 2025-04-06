package io.github.nachowski.providers;


public class Model {
    public final Provider provider;
    public final String modelName;

    private Model(Provider provider, String modelName) {
        this.provider = provider;
        this.modelName = modelName;
    }

    public static Model of(Provider provider, String modelName) {
        return new Model(provider, modelName);
    }

    /**
     * Default model
     * @return OpenAI gpt-4o
     */
    public static Model defaultModel() {
        return new Model(Providers.OPENAI, "gpt-4o");
    }

}
