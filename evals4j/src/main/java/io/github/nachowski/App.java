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
        Model model = Model.of(OPENAI, "gpt-4o");
        final String prompt = "You are a helpful assistant. Answer the question precisely.\n";

        // Create an evaluator model (differs from the tested model)
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
                
        // Run the test
        Result result = evaluator.evaluate(suite);

        // Print the results
        result.getTestCaseResults().forEach(tcResult -> {
            System.out.println(tcResult.toString());
        });

        // Check the overall eval threshold
        assert result.getPassPercentage() > 70.0 : "Pass percentage should be greater than 70%";
    }
}
