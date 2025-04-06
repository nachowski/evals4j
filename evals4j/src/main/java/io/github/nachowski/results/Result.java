package io.github.nachowski.results;

import java.util.List;

public class Result {
    private final List<TestCaseResult> testCaseResults;
    private final double passPercentage;

    private Result(List<TestCaseResult> testCaseResults, double passPercentage) {
        this.testCaseResults = testCaseResults;
        this.passPercentage = passPercentage;
    }

    public static Result of(List<TestCaseResult> testCaseResults, double passPercentage) {
        return new Result(testCaseResults, passPercentage);
    }

    public List<TestCaseResult> getTestCaseResults() { return testCaseResults; }
    public double getPassPercentage() { return passPercentage; }
}
