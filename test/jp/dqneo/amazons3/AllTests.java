package jp.dqneo.amazons3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    FileFinderTest.class,
    TimerTest.class,
    UploadableFileTest.class,
    CLITest.class,

})
public class AllTests {

}
