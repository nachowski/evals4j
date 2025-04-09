package io.github.nachowski;

import io.github.nachowski.evals.Completion;
import io.github.nachowski.providers.Model;
import static io.github.nachowski.providers.Providers.ANTHROPIC;
import static io.github.nachowski.providers.Providers.GEMINI;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest extends TestCase {

    public AppTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( AppTest.class );
    }

    public void testBasicCompletionWithOpenAi() {
        Completion completion = new Completion(Model.defaultModel());

        String response = completion.getCompletion(
            null,
            "Write a one-sentence bedtime story about a unicorn."
        );

        System.out.println(response);
        assertNotNull(response);
    }

    public void testBasicCompletionWithAnthropic() {
        Completion completion = new Completion(Model.of(ANTHROPIC, "claude-3-haiku-20240307"));

        String response = completion.getCompletion(
            null,
            "Write a one-sentence bedtime story about a unicorn."
        );

        System.out.println(response);
        assertNotNull(response);
    }

    public void testBasicCompletionWithGemini() {
        Completion completion = new Completion(Model.of(GEMINI, "gemini-2.0-flash"));

        String response = completion.getCompletion(
            null,
            "Write a one-sentence bedtime story about a unicorn."
        );

        System.out.println(response);
        assertNotNull(response);
    }
}
