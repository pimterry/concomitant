package org.concomitant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestThreadTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void shouldInitialiseBlankly() {
        TestThread thread = buildTestThread();
        assertEquals(null, thread.getThread());
        assertEquals(null, thread.getThrown());
        assertFalse(thread.threwException());
    }
    
    @Test
    public void shouldSetAndGetThread() {
        Thread mockThread = mock(Thread.class);
        
        TestThread thread = buildTestThread();
        thread.setThread(mockThread);
        
        assertEquals(mockThread, thread.getThread());
    }
    
    @Test
    public void shouldSetAndGetThrownExceptions() {
        Throwable throwable = new Throwable();
        
        TestThread thread = buildTestThread();
        
        thread.setThrownException(throwable);
        
        assertTrue(thread.threwException());        
        assertEquals(throwable, thread.getThrown());
    }
    
    @Test
    public void shouldNotAllowSettingThreadTwice() {
        TestThread thread = buildTestThread();
        
        thread.setThread(mock(Thread.class));
        
        exception.expect(ConcomitantMisuseError.class);
        thread.setThread(mock(Thread.class));
    }

    private TestThread buildTestThread() {
        return new TestThread() {
            public void run() throws Throwable {
            }
        };
    }

}
