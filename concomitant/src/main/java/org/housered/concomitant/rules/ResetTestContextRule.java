package org.housered.concomitant.rules;

import org.housered.concomitant.TestContext;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ResetTestContextRule implements TestRule {

    private TestContext testContext;

    public ResetTestContextRule(TestContext context) {
        this.testContext = context;
    }
    
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } finally {
                    testContext.reset();
                }
            }
        };
    }
    
}
