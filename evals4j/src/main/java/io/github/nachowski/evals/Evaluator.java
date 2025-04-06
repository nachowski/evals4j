package io.github.nachowski.evals;

import io.github.nachowski.providers.Model;
import io.github.nachowski.results.Result;

public class Evaluator {
    public static class Builder {
        private Model model;
        private String instructions;

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder instructions(String instructions) {
            this.instructions = instructions;
            return this;
        }

        public Evaluator build() {
            return new Evaluator(model, instructions);
        }
    }

    private final Model model;
    private final String instructions;

    private Evaluator(Model model, String instructions) {
        this.model = model;
        this.instructions = instructions;
    }

    public Model getModel() {
        return model;
    }

    public String getInstructions() {
        return instructions;
    }

    public Result evaluate(EvaluationSuite suite) {
        return null;
    }
}
