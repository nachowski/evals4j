package io.github.nachowski;

import java.util.List;

import io.github.nachowski.evals.EvaluationSuite;
import io.github.nachowski.evals.Evaluator;
import io.github.nachowski.evals.TestCase;
import io.github.nachowski.providers.Model;
import static io.github.nachowski.providers.Providers.ANTHROPIC;
import static io.github.nachowski.providers.Providers.OPENAI;
import io.github.nachowski.results.Result;

/**
 * This is an example of how to use evals4j
 */
public class Example {
    // You'd probably want to use a testcase instead of a main method
    // in a real-world scenario, but this is just an example.
    public static void main( String[] args ) {

        // This is a model under test for this eval.
        Model model = Model.of(ANTHROPIC, "claude-3-haiku-20240307");

        // This is the prompt under test for this eval.
        final String prompt = "Please answer the following question:\n";

        // The evaluator model is used to evaluate the test cases.
        Evaluator evaluator = new Evaluator.Builder()
            .model(Model.of(OPENAI, "gpt-4o")) // The evaluator model differs from the tested model
            .instructions("Check if the response matches the expected output.")
            .build();

        // Define two test cases - in real code you'd use more than two test cases
        TestCase case1 = new TestCase(
            "Please design me a workout for today that features at least 50 reps of pulling leg exercises, at least 50 reps of pulling arm exercises, and ten minutes of core.",
            "A correct answer should include a workout plan with 50 or more reps of pulling leg exercises (such as deadlifts, but not such as squats which are a pushing exercise), 50 or more reps of pulling arm exercises (such as rows, but not such as presses which are a pushing exercise), and ten minutes of core workouts. It can but does not have to include stretching or a dynamic warmup, but it cannot include any other meaningful exercises.");
        
        TestCase case2 = new TestCase(
            "Send Jane an email asking her to meet me in front of the office at 9am to leave for the retreat.",
            "A correct answer should decline to send the email since the assistant has no capabilities to send emails. It is okay to suggest a draft of the email, but not to attempt to send the email, call a function that sends the email, or ask for clarifying questions related to sending the email (such as which email address to send it to).");

        // Create the evaluation suite
        EvaluationSuite suite = new EvaluationSuite.Builder()
            .prompt(prompt)
            .model(model)
            .testCases(List.of(case1, case2))
            .build();
                
        // Run all the evals - this generates completions twice, once for the test cases and once for the evals
        Result result = evaluator.evaluate(suite);

        // Print the individual results (optional)
        result.testCaseResults.forEach(r -> {
            System.out.println(r.toString());
        });

        // Check the overall eval threshold - use assertations in real code
        if (result.passPercentage < 90) {
            System.out.println("The model failed the evaluation.");
        } else {
            System.out.println("The model passed the evaluation.");
        }
    }
}
