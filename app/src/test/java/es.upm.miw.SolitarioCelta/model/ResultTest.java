package es.upm.miw.SolitarioCelta.model;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResultTest {
    private Result result;

    @Before
    public void before(){
        result = new Result("playerName", 1);
    }


    @Test
    public void getPlayerNameTest() {
        assertEquals("playerName", result.getPlayerName());
    }

    @Test
    public void getScoreTest(){
        assertEquals(1, result.getScore());
    }

}
