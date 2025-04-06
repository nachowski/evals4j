package io.github.nachowski.evals;

public class Prompts {
    public static String forEvaluation(EvalStrategy strategy, String systemPrompt, String userPrompt, String expectedOutput, String actualOutput) {
        return "You will be provided an actual answer that an assistant gave to a question, and an expected answer that guides you on what makes the answer correct or incorrect.\n"
            + "Here is the question: <systemPrompt>" + systemPrompt + "</systemPrompt>\n<userPrompt>" + userPrompt + "</userPrompt>\n"
            + "Here is the actual answer: <expected>" + expectedOutput + "</expected>\n"
            + "Here is the expected answer: <actual>" + actualOutput + "</actual>\n"
            + strategy.getEvalInstructions();
    }
}
