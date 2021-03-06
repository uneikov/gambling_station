package com.uran.gamblingstation.service;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

import static com.uran.gamblingstation.Profiles.DB_ACTIVE;
import static com.uran.gamblingstation.Profiles.DB_IMPLEMENTATION;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-database.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({DB_ACTIVE, DB_IMPLEMENTATION})
@Sql(scripts = "classpath:database/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class AbstractServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractServiceTest.class);

    private static StringBuilder results = new StringBuilder();

    protected static String testName;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {

        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            LOG.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResults() {
        results =
                new StringBuilder("\n\n-------" + testName + "---------\n")
                        .append("\n---------------------------------")
                        .append("\nTest                 Duration, ms")
                        .append("\n---------------------------------\n")
                        .append(results)
                        .append("---------------------------------\n");
        LOG.info(results.toString());
        results.setLength(0);
    }
}