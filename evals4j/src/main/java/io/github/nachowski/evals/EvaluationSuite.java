package io.github.nachowski.evals;

import java.util.List;

import io.github.nachowski.providers.Model;

public class EvaluationSuite {
    public final Model model;
    public final List<TestCase> testCases;
    public final String prompt;
    public final EvalStrategy strategy;

    private EvaluationSuite(Model model, List<TestCase> testCases, String prompt, EvalStrategy strategy) {
        this.model = model;
        this.testCases = testCases;
        this.prompt = prompt;
        this.strategy = strategy;
    }

    public static class Builder {
        private Model model;
        private List<TestCase> testCases;
        private String prompt;
        private EvalStrategy strategy;

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder testCases(List<TestCase> testCases) {
            this.testCases = testCases;
            return this;
        }

        public EvaluationSuite build() {
            return new EvaluationSuite(model, testCases, prompt, (strategy == null ? new CotClassifyStrategy() : strategy));
        }

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Builder strategy(EvalStrategy strategy) {
            this.strategy = strategy;
            return this;
        }
    }
}
