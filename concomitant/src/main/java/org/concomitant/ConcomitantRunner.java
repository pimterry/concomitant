package org.concomitant;

import java.util.List;

import org.concomitant.rules.ConcomitantTestRule;
import org.junit.rules.TestRule;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class ConcomitantRunner extends BlockJUnit4ClassRunner {

    private ConcomitantTestRule testRule = new ConcomitantTestRule(TestContext.context());
    
    public ConcomitantRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }
    
    @Override
    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> rules = super.getTestRules(target);
        rules.add(testRule);
        return rules;
    }

}
