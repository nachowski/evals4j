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
        Model model = Model.of(ANTHROPIC, "claude-3-5-haiku");

        // This is the prompt under test for this eval.
        final String prompt = "Please answer the following question:\n";


        Evaluator evaluator = new Evaluator.Builder()
            .model(Model.of(OPENAI, "gpt-4o")) // The evaluator model differs from the tested model
            .instructions("Check if the response matches the expected output.")
            .build();

        // Define two test cases
        TestCase case1 = new TestCase("What is 2 + 2?", "4");
        TestCase case2 = new TestCase("What is the capital of France?", "Paris");

        EvaluationSuite suite = new EvaluationSuite.Builder()
            .prompt(prompt)
            .model(model)
            .testCases(List.of(case1, case2))
            .build();
                
        // Run all the evals
        Result result = evaluator.evaluate(suite);

        // Print the individual results (optional)
        result.getTestCaseResults().forEach(r -> {
            System.out.println(r.toString());
        });

        // Check the overall eval threshold
        assert result.getPassPercentage() >= 50.0 : "Pass percentage should be greater than 50%";
    }
}
