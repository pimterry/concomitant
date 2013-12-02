package org.concomitant;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class ThreadStateMatcher extends TypeSafeMatcher<TestThread> {

    private final Thread.State expectedState;
    private Thread.State actualState;
    
    public ThreadStateMatcher(Thread.State state) {
        this.expectedState = state;
    }
    
    @Override
    protected boolean matchesSafely(TestThread thread) {
        actualState = thread.getThread().getState();
        return actualState.equals(expectedState);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Thread in state ").appendValue(expectedState);
    }
    
    @Override
    protected void describeMismatchSafely(TestThread item, Description mismatchDescription) {
        mismatchDescription.appendText("was a thread in state " + actualState);
        mismatchDescription.appendValueList(" with stacktrace:\n", "\n", "", item.getThread().getStackTrace());
    }

}
