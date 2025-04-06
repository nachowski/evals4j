package io.github.nachowski.evals;

import java.util.List;

import io.github.nachowski.providers.Model;

public class EvaluationSuite {
    private final Model model;
    private final List<TestCase> testCases;

    private EvaluationSuite(Model model, List<TestCase> testCases) {
        this.model = model;
        this.testCases = testCases;
    }

    public Model getModel() {
        return model;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public static class Builder {
        private Model model;
        private List<TestCase> testCases;

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder testCases(List<TestCase> testCases) {
            this.testCases = testCases;
            return this;
        }

        public EvaluationSuite build() {
            return new EvaluationSuite(model, testCases);
        }

        public Builder prompt(String prompt) {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'prompt'");
        }
    }
}
