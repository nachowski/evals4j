package io.github.nachowski.results;

import io.github.nachowski.evals.TestCase;

public class TestCaseResult {
    private final TestCase testCase;
    private final String actualOutput;
    private final boolean passed;

    private TestCaseResult(TestCase testCase, String actualOutput, boolean passed) {
        this.testCase = testCase;
        this.actualOutput = actualOutput;
        this.passed = passed;
    }

    public static TestCaseResult of(TestCase testCase, String actualOutput, boolean passed) {
        return new TestCaseResult(testCase, actualOutput, passed);
    }

    public TestCase getTestCase() { return testCase; }
    public String getActualOutput() { return actualOutput; }
    public boolean isPassed() { return passed; }

    @Override
    public String toString() {
        return "TestCaseResult{" +
                "testCase=" + testCase +
                ", actualOutput='" + actualOutput + '\'' +
                ", passed=" + passed +
                '}';
    }    
}
