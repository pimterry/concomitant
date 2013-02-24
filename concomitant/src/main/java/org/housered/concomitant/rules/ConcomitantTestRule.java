package org.housered.concomitant.rules;

import java.util.ArrayList;
import java.util.List;

import org.housered.concomitant.TestThread;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class ConcomitantTestRule implements TestRule {
    
    List<TestThread> testThreads = new ArrayList<TestThread>();

    @Override
    public Statement apply(Statement statement, Description description) {
        statement = new AlwaysRunTestThreads().apply(statement, description);
        statement = new EnsureTestThreadsTerminate().apply(statement, description);
        statement = new EnsureTestThreadsAllSucceed().apply(statement, description);
        statement = new TestThreadMonitorRule().apply(statement, description);
        
        return statement;
    }

    
    
}
