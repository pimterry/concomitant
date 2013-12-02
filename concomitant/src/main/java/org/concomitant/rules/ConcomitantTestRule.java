package org.concomitant.rules;

import org.concomitant.TestContext;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ConcomitantTestRule implements TestRule {
    
    private TestContext testContext;

    public ConcomitantTestRule(TestContext context) {
        this.testContext = context;
    }

    @Override
    public Statement apply(Statement statement, Description description) {
        statement = new AlwaysRunTestThreads(testContext).apply(statement, description);
        statement = new EnsureTestThreadsTerminate(testContext).apply(statement, description);
        statement = new EnsureTestThreadsAllSucceed(testContext).apply(statement, description);
        statement = new ResetTestContextRule(testContext).apply(statement, description);
        
        return statement;
    }
    
}
