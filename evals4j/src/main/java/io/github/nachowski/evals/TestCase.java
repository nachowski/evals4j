package io.github.nachowski.evals;

public class TestCase {
    public final String input;
    public final String expectedOutput;

    public TestCase(String input, String expectedOutput) {
        this.input = input;
        this.expectedOutput = expectedOutput;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "input='" + input + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                '}';
    }    
}
