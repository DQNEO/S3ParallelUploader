package jp.dqneo.amazons3;

import static org.junit.Assert.*;
import jp.dqneo.amazons3.Timer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetElapsedTime() throws InterruptedException {
        Timer timer = new Timer();
        Thread.sleep(100);
        assertTrue(timer.getElapsedTime() >= 100) ;
        Thread.sleep(100);
        assertTrue(timer.getElapsedTime() >= 200) ;
    }

}
