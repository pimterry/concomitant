package org.housered.concomitant.monitoring;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hamcrest.Matcher;
import org.housered.concomitant.TestThread;
import org.housered.concomitant.TestThreadListener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ThreadSelectorTest {

    @Mock TestThreadListener threadListener;
    @Mock TestThread testThread1;
    @Mock TestThread testThread2;
    @Mock Thread thread1;
    @Mock Thread thread2;
    
    @Mock Matcher<TestThread> threadMatcher;
    
    ThreadSelector selector;
    
    @Before
    public void setup() {
        TestThread.setTestThreadListener(threadListener);
        
        when(testThread1.getThread()).thenReturn(thread1);
        when(testThread2.getThread()).thenReturn(thread2);
        
        selector = new ThreadSelector(threadMatcher);
    }
    
    @Test
    public void shouldReturnThreadMonitorForGivenTestThreads() {
        ThreadMonitor monitor = selector.forThreads(testThread1, testThread2);
        
        List<TestThread> threads = monitor.getMonitoredThreads();
        
        assertEquals(2, threads.size());
        assertThat(threads, hasItem(testThread1));
        assertThat(threads, hasItem(testThread1));
    }
    
    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })    
    public void shouldReturnThreadMonitorForGivenThreads() {
        ThreadMonitor monitor = selector.forThreads(thread1, thread2);
        
        List<TestThread> threads = monitor.getMonitoredThreads();
        
        assertEquals(2, threads.size());
        assertThat(threads, (Matcher) not(hasItem(nullValue(TestThread.class))));
    }

}
