package org.housered.concomitant;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runners.model.InitializationError;

public final class FunctionalTestUtils {

    private FunctionalTestUtils() { }
    
    public static Result runConcomitantTestClass(Class<?> klass) throws InitializationError {
        ConcomitantRunner runner = new ConcomitantRunner(klass);
        Request request = Request.runner(runner);
        return new JUnitCore().run(request);
    }
    
}
