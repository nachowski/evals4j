package io.github.nachowski;

import java.util.List;

import io.github.nachowski.evals.EvaluationSuite;
import io.github.nachowski.evals.Evaluator;
import io.github.nachowski.evals.TestCase;
import io.github.nachowski.providers.Model;
import static io.github.nachowski.providers.Providers.OPENAI;
import io.github.nachowski.results.Result;


public class App {
    public static void main( String[] args ) { // This will move to the /examples folder later

        // This is a model under test for this eval
        Model model = Model.defaultModel();

        // This is the prompt that will be used for the test cases
        final String prompt = "You are a helpful assistant. Answer the question precisely.\n";

        // The evaluator model differs from the tested model
        Evaluator evaluator = new Evaluator.Builder()
            .model(Model.of(OPENAI, "gpt-4.5"))
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
