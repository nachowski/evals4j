package io.github.nachowski.evals;

public interface EvalStrategy {
    String getEvalInstructions();
    boolean passes(String completion);
}
