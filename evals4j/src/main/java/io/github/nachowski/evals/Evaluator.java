package io.github.nachowski.evals;

import java.util.ArrayList;
import java.util.List;

import io.github.nachowski.providers.Model;
import io.github.nachowski.results.Result;
import io.github.nachowski.results.TestCaseResult;

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
        List<String> outputs = new ArrayList<>();
        Completion completion = new Completion(suite.model);
    
        // Iterate over the suite cases and generate completions for each item
        for (TestCase item : suite.testCases) {
            outputs.add(completion.getCompletion(suite.prompt, item.input));
        }

        // Evaluate the outputs using the strategy
        List<TestCaseResult> results = new ArrayList<>();
        for (int i = 0; i < suite.testCases.size(); i++) {
            TestCase testCase = suite.testCases.get(i);
            String output = outputs.get(i);
            boolean passed = suite.strategy.passes(output);
            results.add(TestCaseResult.of(testCase, output, passed));
        }

        long passedCount = results.stream().filter(result -> result.isPassed()).count();
        double passPercentage = (double) passedCount / results.size() * 100;
        return Result.of(results, passPercentage);
    }
}
