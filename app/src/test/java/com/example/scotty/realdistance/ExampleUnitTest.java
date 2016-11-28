package com.example.scotty.realdistance;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void previousDayWorks() throws Exception {
        String date = "Nov 27, 2016";
        PreviousDay prev = new PreviousDay();
        String yesterday = prev.giveMeThePreviousDay(date);

        assertEquals(yesterday, "Nov 26, 2016");
    }
    @Test
    public void previousMonthOrYearWorks() throws Exception {
        String date = "Nov 1, 2016";
        PreviousDay prev = new PreviousDay();
        String yesterday = prev.giveMeThePreviousDay(date);

        assertEquals(yesterday, "Oct 31, 2016");
    }

}