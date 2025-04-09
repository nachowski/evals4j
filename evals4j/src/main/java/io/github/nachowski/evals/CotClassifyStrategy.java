package io.github.nachowski.evals;

/**
 * CotClassifyStrategy is the default strategy for evaluating models using Chain of Thought (CoT) classification.
 */
public class CotClassifyStrategy implements EvalStrategy {
    private final static String DEFAULT_INSTRUCTIONS = 
        "First, think through whether the answer is correct or incorrect based on provided expected answer. "
        + "The answer should match the key points of the expected answer in breadth and depth. "
        +"Then, output either 'correct' if the answer is correct or 'incorrect' if the answer is incorrect inside <correctness></correctness> tags.";
    private final static String TAG_NAME = "correctness";
    private static final String START_TAG = "<" + TAG_NAME + ">";
    private static final String END_TAG = "</" + TAG_NAME + ">";

    @Override
    public String getEvalInstructions() {
        return DEFAULT_INSTRUCTIONS;
    }

    @Override
    public boolean passes(String completion) {
        if (completion == null) {
            return false;
        }

        int startIndex = completion.indexOf(START_TAG);
        if (startIndex == -1) {
            return false; // Start tag not found
        }

        int contentStart = startIndex + START_TAG.length();
        int endIndex = completion.indexOf(END_TAG, contentStart);
        if (endIndex == -1 || endIndex <= contentStart) {
            return false; // End tag missing or no content between tags
        }

        String content = completion.substring(contentStart, endIndex).trim();
        return content.equalsIgnoreCase("correct");
    }
}
